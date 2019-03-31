const monteCarloElt = document.getElementById('montecarlo_');
const diagramElt = monteCarloElt.querySelector('.montecarlo');
const btn = monteCarloElt.querySelector('button');
const resultElt = monteCarloElt.querySelector('.result');
const outputElt = resultElt.querySelector('output');
const inElt = resultElt.querySelector('.info .in');
const outElt = resultElt.querySelector('.info .out');
const countElt = resultElt.querySelector('.info .count');

const acc = {count: 0, inCircle: 0};

function newPoint() {
    const x = Math.random();
    const y = Math.random();
    const inCircle = (x * x + y * y) <= 1;

    acc.count += 1;
    if (inCircle) {
        acc.inCircle += 1;
    }

    const pointElt = document.createElement('span');
    pointElt.classList.add(inCircle ? 'in' : 'out');
    pointElt.style.left = `${x * 100}%`;
    pointElt.style.top = `${100 - y * 100}%`;

    diagramElt.appendChild(pointElt);

    outputElt.innerHTML = acc.inCircle / acc.count * 4;
    inElt.innerHTML = acc.inCircle;
    outElt.innerHTML = acc.count - acc.inCircle;
    countElt.innerHTML = acc.count;
}


let points = 10;
let threshold = 10;
btn.innerHTML = points;
btn.addEventListener('click', () => {
    resultElt.style.opacity = 1;
    for (let i = 0; i < points; i++) {
        newPoint();
    }
    threshold--;
    if (threshold === 0) {
        threshold = 10;
        points *= 10;
        btn.innerHTML = points;
    }
});
