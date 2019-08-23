class SideBar extends Component {
    constructor() {
        super();

        this.records = {};
    }

    render() {
        return `
        <div id="menu">
            <div class="pure-menu">
            <div class="pure-menu-heading" href="#">Record states</div>
                <table class="pure-table">
                    ${Object.keys(this.records).map(uid => {
                        return `<tr>
                            <td>${uid}</td>
                            <td>${this.records[uid]}</td>
                        </tr>`;
                    }).join('\n')}
                </table>
            </div>
    `;
    }

    update() {
        const element = document.querySelector('SideBar');
        console.log('sidebar update');
        
        element.innerHTML = this.render();
    }
}