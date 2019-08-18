class ObsGraph extends Component {
    constructor() {
        super();
    }

    render() {
        return `
        <canvas id="ObsGraph" width="100%" height="500px">
        </canvas>
    `;
    }

    afterRender() {
        const canvas = document.querySelector("#ObsGraph");
        console.log(canvas);
        console.log(canvas.parentElement);

        const c = canvas.getContext('2d');

        c.fillRect(10, 100, 10, 10);
        c.fillRect(10, 200, 10, 10);
        c.fillRect(10, 300, 10, 10);
        c.fillRect(10, 400, 10, 10);

        c.fillRect(10, 100, 10, 10);
        c.fillRect(10, 200, 10, 10);
        c.fillRect(10, 300, 10, 10);
        c.fillRect(10, 400, 10, 10);
    }
}