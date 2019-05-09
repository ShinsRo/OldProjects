import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { BASE_URL } from "../../supports/API_CONSTANT";

class KssPushMsgReceiver {
    constructor(mid) {
        this.mid = mid;
        this.connected = false;
        
        this.sessionId = '';
        this.transactionId = -1;

        this.socket = new SockJS(`${BASE_URL}/ws`);
        this.stompClient = Stomp.over(this.socket);
        this.currentMsg = 'NO MESSAGE';

        this.connect = this.connect.bind(this);
        this.disconnect = this.disconnect.bind(this);

        this.register = this.register.bind(this);
        this.unregister = this.unregister.bind(this);

        this.getMessageJSON = this.getMessageJSON.bind(this);

        this.setConnected = this.setConnected.bind(this);
    }

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

    connect(dirContainer) {
        const { socket, stompClient, register } = this;
        this.stompClient.connect({}, frame => {
            console.info('Connected: ' + frame);
            
            const urlArray = socket._transport.url.split('/');
            const idx = urlArray.length - 2;
            const sessionId = urlArray[idx];
            
        stompClient.subscribe('/globalChannel/notify', message => { /* 글로벌 메세지 */ });
            // 프로젝트 변동 알림 채널
            stompClient.subscribe(`/user.${sessionId}/updateProject`, message => {
                let pDto = JSON.parse(message.body);
                // const $ = window.$;

                // const $globalToast = $("#globalToast");
                // console.log($globalToast);
                
                // $globalToast.toast('show');
                
                dirContainer.correctProject(pDto);
            });
            register(sessionId);
        }, err => {});
        this.setConnected(true);
        return;
    }

    register(sessionId) {
        this.sessionId = sessionId;
        this.stompClient.send(`/app/register.${this.mid}`, {}, this.getMessageJSON('register'));
    }

    disconnect() {
        const stompClient = this.stompClient;
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        this.unregister();
        this.setConnected(false);
        console.log("Disconnected");
    }

    unregister() {
        this.stompClient.send(`/app/unregister.${this.mid}`, {}, this.getMessageJSON(''));
    }

    setConnected(connected) {
        this.connected = connected;
    }
}

export default KssPushMsgReceiver;