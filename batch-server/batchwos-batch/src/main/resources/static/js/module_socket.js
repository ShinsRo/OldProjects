class ObserverSocket {
    constructor() {
        this.socket = null;
        this.templates = null;
    }

    setTemplates(templates) { this.templates = templates; }

    listen() {
        this.socket = new WebSocket("ws://127.0.0.1:9400/wsEndpoint");
        console.log(this.socket.readyState);
        
        this.socket.addEventListener('open', () => {
            console.log('Socket Opened');
        });

        this.socket.addEventListener('message', (msg) => {
            
            const msgObj = JSON.parse(msg.data);
            this.msgDispatch(msgObj);
        });
    }

    msgDispatch(msgObj) {
        console.log(msgObj);
        switch (msgObj.type) {
            case 'FLOW':
                const SideBar = this.templates.SideBar;
                SideBar.records[msgObj.UID] = msgObj.state;

                SideBar.update();


                const obsGraph = this.templates.ObsGraph;

                if (!obsGraph.nodes[msgObj.from]) {
                    obsGraph.addParser(msgObj.from);
                }

                if (!obsGraph.nodes[msgObj.to]) {
                    obsGraph.addParser(msgObj.to);
                }

                obsGraph
                    .nodes[msgObj.from]
                    .edges[msgObj.to].weight += 1;

                obsGraph.draw();
                obsGraph.blinkEdge(msgObj.from, msgObj.to);
                break;
        
            case 'JOB_STATE':
                const StepStates = this.templates.StepStates;
                const stepState = msgObj.stepState;

                if (msgObj.jobName === 'add') {
                    const target = StepStates.jobState['addJob'];
                    target['search']    = stepState['search']['state'];
                    target['retrieve']  = stepState['retrieve']['state'];
                    target['convert']   = stepState['convert']['state'];
                } else if (msgObj.jobName === 'update') {
                    const target = StepStates.jobState['updateJob'];

                    target['fetchAndUpdate'] = stepState['fetchAndUpdate']['state'];
                }

                StepStates.update();

                break;
            case 'increaseElement':

                const en = msgObj.elementName;
                const amount = msgObj.amount;


                break;
            default:
                break;
        }
    }
}