import React, { Component } from 'react';
import Modal from 'react-awesome-modal';
import stores from '../../../../stores';
import DetailView from './DetailView';
import CurrectForm from './CurrectForm';

class ProfileModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            viewLevel: 'DETAIL',
            correctDto: {},
        }

        this.changeViewLevel = this.changeViewLevel.bind(this);
        this.closeModal = this.closeModal.bind(this);
    }

    changeViewLevel() {
        this.setState({ viewLevel: 'CURRECT' })
    }

    closeModal() {
        this.props.closeModal('visible');
        this.setState({ viewLevel: 'DETAIL' });
    }

    render() {
        const { userState } = stores.getState();
        const { memberInfo } = userState.userInfo;

        let lastCareer = memberInfo.career;
        if (Array.isArray(memberInfo.career)) {
          const career = [...memberInfo.career];
    
          while(career.length) {
            const last = career.pop();
            if (!last.endDate) {
              lastCareer = last;
              break;
            }
          }
        }

        let content;
        if (this.state.viewLevel === 'DETAIL') {
            content = <DetailView memberInfo={memberInfo} lastCareer={lastCareer} closeModal={this.closeModal} changeViewLevel={this.changeViewLevel}/>
        } else if (this.state.viewLevel === 'CURRECT') {
            content = <CurrectForm memberInfo={memberInfo} lastCareer={lastCareer} closeModal={this.closeModal} changeViewLevel={this.changeViewLevel}/>;
        }

        return (
            <Modal visible={this.props.visible} width="20%" onClickAway={this.closeModal}>
                {content}
            </Modal>
        );
    }
}

export default ProfileModal;