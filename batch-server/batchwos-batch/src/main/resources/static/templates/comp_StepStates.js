class StepStates extends Component {
    constructor() {
        super();

        this.jobState = {
            'addJob': {
                'search': 'waiting',
                'retrieve': 'waiting',
                'convert': 'waiting',
            },
            'updateJob': {
                'fetchAndUpdate': 'waiting'
            },
        }
    }

    render() {
        const search = this.jobState['addJob']['search'];
        const retrieve = this.jobState['addJob']['retrieve'];
        const convert = this.jobState['addJob']['convert'];
        
        const fetchAndUpdate = this.jobState['updateJob']['fetchAndUpdate'];

        return `
        <h2 class="content-subhead black">JOB/STEP STATE</h2>

        <table class="pure-table black" style="text-align: center;">
            <tr>
                <th>JobName</th><th>searchStep</th><th>retrieveStep</th><th>convertStep</th>
            </tr>
            <tr>
                <td>addJob</td>
                <td class="bg-color-${search}">${search}</td>
                <td class="bg-color-${retrieve}">${retrieve}</td>
                <td class="bg-color-${convert}">${convert}</td>
            </tr>
        </table>
        <br/>
        <table class="pure-table black" style="text-align: center;">
            <tr>
                <th>JobName</th><th colspan="3">fetchAndUpdateStep</th>
            </tr>
            <tr>
                <td>updateJob</td>
                <td class="bg-color-${fetchAndUpdate}" colspan="3">${fetchAndUpdate}</td>
            </tr>
        </table>
    `;
    }

    update() {
        const element = document.querySelector('StepStates');
        console.log('StepStates update');
        
        element.innerHTML = this.render();
    }
}