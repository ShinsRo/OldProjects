class ObsGraph extends Component {
    constructor() {
        super();
        this.overlayIdx = 0;

        this.parsersCnt = 0;
        this.parsers = {};

        this.nodes = {
            'batchServer': {
                x: 100, y: 400,
                texts: ['배치 서버', '0.0.0.127:9400'],
                edges: {'broker': {type: 0, weight: 0}}
            },
            'broker': {
                x: 400, y: 100,
                texts: ['MQ', '0.0.0.127:5674'],
                edges: {
                    // 2: {type: 1, weight: 0},
                }
            },
            // 2: {
            //     x: 700, y: 100,
            //     texts: ['파서01', '0.0.0.127', 'ps:10532'],
            //     edges: {
            //         'batchServer'   : {type: 0, weight: 0},
            //         'broker'        : {type: 2, weight: 0}
            //     }
            // },
        }

    }

    render() {
        return `
        <canvas id="ObsGraph"           style="position: relative;" width="800px" height="500px"></canvas>
        <canvas id="ObsGraphOverlay"    style="position: absolute;" width="800px" height="500px"></canvas>
        <canvas id="ObsGraphOverlay"    style="position: absolute;" width="800px" height="500px"></canvas>
        <canvas id="ObsGraphOverlay"    style="position: absolute;" width="800px" height="500px"></canvas>
        <canvas id="ObsGraphOverlay"    style="position: absolute;" width="800px" height="500px"></canvas>
    `;
    }

    addParser(ps) {
        this.parsers[ps] = {
            x: 700, y: ++this.parsersCnt * 100,
            texts: [`파서0${this.parsersCnt}`, '0.0.0.127', `ps:${ps}`],
            edges: {
                'batchServer'   : {type: 0, weight: 0},
                'broker'        : {type: 2, weight: 0}
            }
        }

        this.nodes[ps] = this.parsers[ps];
        this.nodes['broker'].edges[ps] = {type: 1, weight: 0};
        console.log(this.nodes);
        
    }

    drawNode(ctx, x, y, w, h, r, texts) {
        ctx.beginPath();

        ctx.arc(x + w/2 - r, y + h/2 - r, r, 0.0 * Math.PI, 0.5 * Math.PI);
        ctx.arc(x - w/2 + r, y + h/2 - r, r, 0.5 * Math.PI, 1.0 * Math.PI);
        ctx.arc(x - w/2 + r, y - h/2 + r, r, 1.0 * Math.PI, 1.5 * Math.PI);
        ctx.arc(x + w/2 - r, y - h/2 + r, r, 1.5 * Math.PI, 2.0 * Math.PI);
        ctx.lineTo(x + w/2, y + h/2 - r)

        ctx.textAlign = "center";

        ctx.fillText(texts[0], x, y - h/2 - 10);
        for (let i = 1; i < texts.length; i++) {
            const text = texts[i];
            ctx.fillText(text, x, y - h/2 + i * 10);
        }

        ctx.stroke();
    }

    drawEdge(ctx, type, x, y, tx, ty, text) {
        let yTrans = 0;
        if (type === 1) yTrans += 8;
        if (type === 2) yTrans -= 8;

        y += yTrans;
        ty += yTrans;

        ctx.beginPath();
        ctx.moveTo(x, y);

        ctx.lineTo((((tx - x) * 0.4 + x)), (ty - y) * 0.4 + y)

        ctx.font = "15px";
        ctx.textAlign = "center";
        ctx.fillText(text, (((tx - x) * 0.5 + x)), (ty - y) * 0.5 + y + 3)

        ctx.moveTo((((tx - x) * 0.6 + x)), (ty - y) * 0.6 + y)
        ctx.lineTo((((tx - x) * 1.0 + x)), (ty - y) * 1.0 + y)

        const arrowLen = 10;
        const ax = (tx - x) * 0.4 + x, ay = (ty - y) * 0.4 + y;
        
        this.drawArrow(ctx, x, y, ax, ay, arrowLen);
        ctx.stroke();
    }
    
    drawArrow(ctx, x, y, ax, ay, arrowLen) {
        const angle = Math.atan2(ay-y,ax-x);
        ctx.moveTo(ax, ay)
        ctx.lineTo(
            ax - arrowLen * Math.cos(angle - Math.PI/7), 
            ay - arrowLen * Math.sin(angle - Math.PI/7)
        );

        ctx.moveTo(((ax)), ay)
        ctx.lineTo(
            ax - arrowLen * Math.cos(angle + Math.PI/7), 
            ay - arrowLen * Math.sin(angle + Math.PI/7)
        );
    }

    blinkEdge(fromIdx, toIdx) {
        const canvas = document.querySelectorAll("#ObsGraphOverlay")[this.overlayIdx];
        
        this.overlayIdx = (this.overlayIdx + 1) % 4;
        canvas.style.letterSpacing = '1px';

        const ctx = canvas.getContext('2d');

        const fnode = this.nodes[fromIdx];
        const tNode = this.nodes[toIdx];
        const edge  = fnode.edges[toIdx];
        
        ctx.strokeStyle = 'orange';
        this.drawEdge(ctx, edge.type, fnode.x, fnode.y, tNode.x, tNode.y, '');
        
        window.setTimeout(() => {
            ctx.clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);
        }, 1000);
    }

    // 호출한 곳에서 직접 draw를 호출.
    updateNodeTexts(nid, texts) {
        const node = this.nodes[nid];
        node.texts = texts;
    }

    // 호출한 곳에서 직접 draw를 호출.
    updateEdgeWeight(from, to, weight) {
        const fNode = this.nodes[from];
        const edge = fNode.edges[to];
        edge.weight = weight;
    }

    draw() {
        const canvas = document.querySelector("#ObsGraph");
        canvas.style.letterSpacing = '1px';

        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.clientWidth, canvas.clientHeight);

        Object.keys(this.nodes).forEach(nid => {
            ctx.strokeStyle = 'black';

            const node = this.nodes[nid];
            this.drawNode(ctx, node.x, node.y, 100, 75, 10, node.texts);

            Object.keys(node.edges).forEach(tnid => {
                const to    = this.nodes[tnid];
                const edge  = node.edges[tnid];

                this.drawEdge(ctx, edge.type, node.x, node.y, to.x, to.y, edge.weight);
            });
        })
    }

    afterRender() {
        this.draw();
    }
}