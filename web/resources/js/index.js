let points = [];
let redrawGraphView = () => drawView(1);

const correctImg = new Image();
const incorrectImg = new Image();
correctImg.src = 'resources/pictures/correct.png';
incorrectImg.src = 'resources/pictures/incorrect.png';


function drawView(r) {
    const graph = document.getElementById('graph');
    const context = graph.getContext('2d');

    context.clearRect(0, 0, graph.width, graph.height);
    //круг
    context.beginPath();
    context.moveTo(250, 250);
    context.arc(250, 250, 230, Math.PI, -Math.PI / 2);
    context.closePath();
    context.strokeStyle = "#39f";
    context.fillStyle = "#39f";
    context.fill();
    context.stroke();

    //треугольник
    context.beginPath();
    context.moveTo(250, 480);
    context.lineTo(480, 250);
    context.lineTo(250, 250);
    context.closePath();
    context.strokeStyle = "#39f";
    context.fillStyle = "#39f";
    context.fill();
    context.stroke();

    //прямоугольник
    context.beginPath();
    context.rect(250, 20, 230, 230);
    context.closePath();
    context.strokeStyle = "#39f";
    context.fillStyle = "#39f";
    context.fill();
    context.stroke();


    //оси
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.beginPath();
    context.font = "14px Courier New";
    context.moveTo(250, 0);
    context.lineTo(250, 500);
    context.moveTo(250, 0);
    context.lineTo(245, 15);
    context.moveTo(250, 0);
    context.lineTo(255, 15);
    context.fillText("Y", 260, 10);
    context.moveTo(0, 250);
    context.lineTo(500, 250);
    context.moveTo(500, 250);
    context.lineTo(485, 245);
    context.moveTo(500, 250);
    context.lineTo(485, 255);
    context.fillText("X", 490, 235);

    // деления
    const R = r == null ? "R" : r;
    const halfR = r == null ? "R/2" : r / 2;

    context.moveTo(245, 20);
    context.lineTo(255, 20);
    context.fillText(R, 255, 25);
    context.moveTo(245, 135);
    context.lineTo(255, 135);
    context.fillText(halfR, 255, 140);
    context.moveTo(245, 365);
    context.lineTo(255, 365);
    context.fillText(`-${halfR}`, 255, 370);
    context.moveTo(245, 480);
    context.lineTo(255, 480);
    context.fillText(`-${R}`, 255, 485);
    context.moveTo(20, 245);
    context.lineTo(20, 255);
    context.fillText(`-${R}`, 15, 240);
    context.moveTo(135, 245);
    context.lineTo(135, 255);
    context.fillText(`-${halfR}`, 130, 240);
    context.moveTo(365, 245);
    context.lineTo(365, 255);
    context.fillText(halfR, 360, 240);
    context.moveTo(480, 245);
    context.lineTo(480, 255);
    context.fillText(R, 475, 240);

    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();

    drawPoints(r, graph, context);
    redrawGraphView = () => drawView(r);

    if (r != null) {
        graph.onclick = (event) => {

            const rect = graph.getBoundingClientRect();
            const visualX = Math.floor(event.clientX - rect.left);
            const visualY = Math.floor(event.clientY - rect.top);

            const centerX = 250;
            const centerY = 250;
            const zoomX = 230 / r;
            const zoomY = 230 / r;

            console.log((visualX - centerX) / zoomX);
            console.log((centerY - visualY) / zoomY);
            console.log(r);

            sendForm((visualX - centerX) / zoomX,(centerY - visualY) / zoomY, r);

        };
    }
}

function sendForm(x,y,r) {
    document.getElementById('formForCanvas:x10').value = parseFloat(x);
    document.getElementById('formForCanvas:y10').value = parseFloat(y);
    document.getElementById('formForCanvas:r10').value = parseFloat(r);
    document.getElementById('formForCanvas:fromcanvas').value = true;
    document.getElementById('formForCanvas:btn10').click();
}

function drawPoints(r, canvas, context) {
    const centerX = 250;
    const centerY = 250;

    if (r != null) {
        const zoomX = 230 / r;
        const zoomY = 230 / r;

        points.forEach((point) => {
            if (point.r === r) {
                const visualX = centerX + point.x * zoomX;
                const visualY = centerY - point.y * zoomY;
                if (point.popadanie === true) {
                    context.drawImage(correctImg, visualX - 7, visualY - 7, 14, 14);
                } else context.drawImage(incorrectImg, visualX - 7, visualY - 7, 14, 14);
            }
        });
    } else {
        points.forEach((point) => {
            const zoomX = 230 / point.r;
            const zoomY = 230 / point.r;
            const visualX = centerX + point.x * zoomX;
            const visualY = centerY - point.y * zoomY;
            if (point.popadanie) {
                context.drawImage(correctImg, visualX - 7, visualY - 7, 14, 14);
            } else context.drawImage(incorrectImg, visualX - 7, visualY - 7, 14, 14);
        });
    }
}


document.body.onload = () => {
    let e = document.getElementById("form:r");
    let strUser = e.value;

    redrawGraphView = () => drawView(parseFloat(strUser.replace(',', '.')));

    let parse = JSON.parse(document.getElementById("historyPoints").innerText);
    if (parse instanceof Array) {
        points = parse;
        redrawGraphView()
    }
};