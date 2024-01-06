export default class Graph {
    svgPoint;

    constructor() {
        this.svgPoint = document.querySelector('svg').createSVGPoint();
    }

    getSvgPoint() {
        return this.svgPoint;
    }

    drawDot(x, y, r, isHit) {
        const radius = 3;
        const svgns = "http://www.w3.org/2000/svg";
        const svg = document.querySelector('svg');
        const dotsObjects = sessionStorage.getItem("dotsObjects") ? JSON.parse(sessionStorage.getItem("dotsObjects")) : [];

        const dotObject = {
            x: x,
            y: y,
            r: r,
            isHit: isHit,
            radius: radius,
        };

        const dot = document.createElementNS(svgns, 'circle');
        dot.setAttributeNS(null, 'cx', x);
        dot.setAttributeNS(null, 'cy', y);
        dot.setAttributeNS(null, 'class', "target-dot");
        dot.setAttributeNS(null, 'r', radius.toString());
        dot.setAttributeNS(null, 'style', 'fill: ' + (isHit ? 'green' : 'red') + '');

        svg.appendChild(dot);

        dotsObjects.push(JSON.stringify(dotObject));

        sessionStorage.setItem('dotsObjects', JSON.stringify(dotsObjects));
    }

    restoreDots() {
        const svg = document.querySelector('svg');

        const svgns = "http://www.w3.org/2000/svg"

        if (sessionStorage.getItem("dotsObjects")) {
            JSON.parse(sessionStorage.getItem("dotsObjects")).forEach(value => {
                const dot = JSON.parse(value);
                const dotElement = document.createElementNS(svgns, 'circle');
                dotElement.setAttributeNS(null, 'cx', dot.x);
                dotElement.setAttributeNS(null, 'cy', dot.y);
                dotElement.setAttributeNS(null, 'class', "target-dot");
                dotElement.setAttributeNS(null, 'r', dot.radius.toString());
                dotElement.setAttributeNS(null, 'style', 'fill: ' + (dot.isHit ? 'green' : 'red'));

                svg.appendChild(dotElement);
            });
        }
    }

    getClickPoint(event) {
        this.svgPoint.x = event.clientX;
        this.svgPoint.y = event.clientY;

        return this.svgPoint.matrixTransform(
            document.querySelector('svg')
                .getScreenCTM()
                .inverse()
        );
    }

    redrawGraph() {
        const r = document.getElementById("graph-form:r-value").value;
        if (r > 5) {
            document.getElementById("graph-form:r-value").setAttribute("value", 5);
        }
        if (r !== undefined && r >= 2 && r <= 5) {
            const triangle = document.getElementById("triangle");
            const rectangle = document.getElementById("rectangle");
            const circle = document.getElementById("circle");
            triangle.setAttributeNS(null, "points", `150,${150 - r * 10} 150,150 ${150 - r * 20},150`);
            rectangle.setAttributeNS(null, "points", `${150 - r * 20},${150 + r * 20} ${150 - r * 20},150 150,150 150,${150 + r * 20}`);
            circle.setAttributeNS(null, "d", `M ${150 + r * 20} 150 A 100 100, 90, 0, 0, 150 ${150 - r * 20} L 150 150 Z`);
            const xPositiveLine1 = document.getElementById("x-pos-1");
            const xPositiveLine2 = document.getElementById("x-pos-2");
            const xNegativeLine1 = document.getElementById("x-neg-1");
            const xNegativeLine2 = document.getElementById("x-neg-2");
            const yPositiveLine1 = document.getElementById("y-pos-1");
            const yPositiveLine2 = document.getElementById("y-pos-2");
            const yNegativeLine1 = document.getElementById("y-neg-1");
            const yNegativeLine2 = document.getElementById("y-neg-2");

            xPositiveLine1.setAttributeNS(null, "x1", `${150 + r * 10}`);
            xPositiveLine1.setAttributeNS(null, "x2", `${150 + r * 10}`);
            xPositiveLine2.setAttributeNS(null, "x1", `${150 + r * 20}`);
            xPositiveLine2.setAttributeNS(null, "x2", `${150 + r * 20}`);

            xNegativeLine1.setAttributeNS(null, "x1", `${150 - r * 10}`);
            xNegativeLine1.setAttributeNS(null, "x2", `${150 - r * 10}`);
            xNegativeLine2.setAttributeNS(null, "x1", `${150 - r * 20}`);
            xNegativeLine2.setAttributeNS(null, "x2", `${150 - r * 20}`);

            yPositiveLine1.setAttributeNS(null, "y1", `${150 - r * 10}`);
            yPositiveLine1.setAttributeNS(null, "y2", `${150 - r * 10}`);
            yPositiveLine2.setAttributeNS(null, "y1", `${150 - r * 20}`);
            yPositiveLine2.setAttributeNS(null, "y2", `${150 - r * 20}`);

            yNegativeLine1.setAttributeNS(null, "y1", `${150 + r * 10}`);
            yNegativeLine1.setAttributeNS(null, "y2", `${150 + r * 10}`);
            yNegativeLine2.setAttributeNS(null, "y1", `${150 + r * 20}`);
            yNegativeLine2.setAttributeNS(null, "y2", `${150 + r * 20}`);

            const xPositiveText1 = document.getElementById("x-pos-text-1");
            const xPositiveText2 = document.getElementById("x-pos-text-2");
            const xNegativeText1 = document.getElementById("x-neg-text-1");
            const xNegativeText2 = document.getElementById("x-neg-text-2");
            const yPositiveText1 = document.getElementById("y-pos-text-1");
            const yPositiveText2 = document.getElementById("y-pos-text-2");
            const yNegativeText1 = document.getElementById("y-neg-text-1");
            const yNegativeText2 = document.getElementById("y-neg-text-2");

            xPositiveText1.setAttributeNS(null, "x", `${150 + r * 10 - 5}`);
            xPositiveText2.setAttributeNS(null, "x", `${150 + r * 20 - 3}`);
            xNegativeText1.setAttributeNS(null, "x", `${150 - r * 10 + 10}`);
            xNegativeText2.setAttributeNS(null, "x", `${150 - r * 20 + 10}`);

            yPositiveText1.setAttributeNS(null, "y", `${150 - r * 10 - 5}`);
            yPositiveText2.setAttributeNS(null, "y", `${150 - r * 20 - 5}`);
            yNegativeText1.setAttributeNS(null, "y", `${150 + r * 10 + 5}`);
            yNegativeText2.setAttributeNS(null, "y", `${150 + r * 20 + 2}`);
        }
    }

    clearDots() {
        document.querySelectorAll("circle:not(#circle)").forEach(value => value.remove());
        sessionStorage.removeItem("dotsObjects");
    }
}