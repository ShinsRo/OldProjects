/** 
 * 소켓 메세지를 받고 프로젝트를 동기화하는 클래스 정의
 * 
 * KssPushMsgReceiver는 접속한 유저와 관련한 프로젝트가
 * 다른 클라이언트로부터 수정되었을 때 알림을 받을 수 있도록
 * 토스트 메세지를 제어하고, 더불어 해당 프로젝트를 동기화하는
 * 클래스이다.
 * 
 * 2019.05.13
 * @file 이 파일은 KssPushMsgReceiver를 정의한다.
 * @author 김승신
 */

import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { BASE_URL } from "../../supports/API_CONSTANT";

class KssPushMsgReceiver {
    /**
     * 필요한 변수를 선언하고 초기화한다.
     * 
     * @param {String} mid 접속한 유저 ID
     */
    constructor(mid) {
        this.mid = mid;                                         // 접속한 유저 ID
        this.connected = false;                                 // 소켓 연결 여부
        
        this.sessionId = '';                                    // 소켓 세션 아이디
        this.transactionId = -1;                                // 메세지 교환 당 트랜젝션 아이디

        this.socket = new SockJS(`${BASE_URL}/ws`);             // 소켓 클래스 (SockJS)
        this.stompClient = Stomp.over(this.socket);             // STOMP client
        this.currentMsg = 'NO MESSAGE';                         // 최근 메세지

        /* 이하 메서드 바인딩 */
        this.connect = this.connect.bind(this);
        this.disconnect = this.disconnect.bind(this);

        this.register = this.register.bind(this);
        this.unregister = this.unregister.bind(this);

        this.getMessageJSON = this.getMessageJSON.bind(this);

        this.setConnected = this.setConnected.bind(this);
        /* 이하 메서드 바인딩 끝 */
    }

    /**
     * 보낼 메세지의 내용을 형식에 맞게 구성하고 JSON 형태로 변환한다. 
     * 
     * @param {String} content 보낼 메세지 내용
     */
    getMessageJSON(content) {
        const message = {
            sessionId: this.sessionId,
            transactionId: ++this.transactionId,
            validUntil: '',
            content: content,
            timestamp: new Date(Date.now()),
        }
        return JSON.stringify(message);
    }

    /**
     * 서버와 소켓 연결과 구독, 핸들링 콜백을 정의한다.
     * 
     * @param {KssTree} dirContainer 프로젝트 디렉토리 관리 컨테이너
     */
    connect(dirContainer) {
        const { socket, stompClient, register } = this;

        // STOMP client 연결 및 콜백 정의
        this.stompClient.connect({}, frame => {
            
            /* Sokcet URL로부터 sessionId 추출 */
            const urlArray = socket._transport.url.split('/');
            const idx = urlArray.length - 2;
            const sessionId = urlArray[idx];
            /* Sokcet URL로부터 sessionId 추출 끝 */
            
            // 글로벌 푸쉬 메세지 채널
            stompClient.subscribe('/globalChannel/notify', message => { /* 글로벌 메세지 */ });

            // 프로젝트 변동 알림 채널
            stompClient.subscribe(`/user.${sessionId}:PROJECT`, message => {
                let pDto = JSON.parse(message.body);    // 프로젝트 DTO
                let timePassed;                         // 변동 후 현재 클라이언트에 도달하기까지 걸린 시간
                const $ = window.$;                     // Jquery 객체
                const $tc = $("#toastContainer");       // 전역 토스트 컴포넌트 아이디

                /* 데이트 포맷팅 에러 방어 */
                try {
                    timePassed = Date.now() - new Date(pDto.udate);
                    timePassed = this.timeFormatter(timePassed);
                } catch (err) {
                    timePassed = '';
                }
                /* 데이트 포맷팅 에러 방어 끝 */
                
                /* 토스트 메세지 DOM 조작 */
                $tc.find('#toastHeader').html(`프로젝트 수정 알림`);
                $tc.find('#toastTime').html(`${timePassed} 전`);
                $tc.find('#toastBody').html(`<span class="font-weight-bold">${pDto.pname}</span>가 최근 수정되었습니다.`);
                
                $tc.toast('show');
                /* 토스트 메세지 DOM 조작 끝 */
                
                // 프로젝트 동기화
                dirContainer.correctProject(pDto);
            });
            register(sessionId);
        }, err => {});
        this.setConnected(true);
        return;
    }

    /**
     * 서버에 등록 메세지를 보낸다.
     * 
     * @param {String} sessionId 
     */
    register(sessionId) {
        this.sessionId = sessionId;
        this.stompClient.send(`/app/register.${this.mid}`, {}, this.getMessageJSON('register'));
    }

    /**
     * 소켓 연결을 해제한다.
     * 
     */
    disconnect() {
        const stompClient = this.stompClient;
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        this.unregister();
        this.setConnected(false);
    }


    /**
     * 서버에 등록 해제 메세지를 보낸다.
     */
    unregister() {
        this.stompClient.send(`/app/unregister.${this.mid}`, {}, this.getMessageJSON(''));
    }

    setConnected(connected) {
        this.connected = connected;
    }

    /**
     * JSON 형태의 DateTime을 사용자 메세지로 변환한다.
     * 
     * @param {String} raw 
     */
    timeFormatter(raw) {
        const time = new Date(raw);
        
        let min = time.getMinutes().toString();
        let sec = time.getSeconds().toString();
        let msec = time.getMilliseconds().toString();
        
        if (min.length < 2) min = '0' + min;
        if (sec.length < 2) sec = '0' + sec;
        while (msec.length < 3) msec = '0' + msec;
        return `${sec}.${msec}초`;
    };
}

export default KssPushMsgReceiver;