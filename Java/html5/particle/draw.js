//

var canvas;
// var position = new Vector2DD(10, 200);
// var velocity = new Vector2DD(50, -50);
// var acceleration = new Vector2DD(0, 10);
var dt = 0.01;
var ctx;
var timer;
var speed = 300;
var isRunning = false;
var ps;

var oldMousePosition = Vector2D.zero, newMousePosition = Vector2D.zero;

function step() {
	var velocity = newMousePosition.subtract(oldMousePosition).multiply(10);
	var mold = velocity.length();
	if (mold > 0) {
		velocity = velocity.add(sampleDirection(0, Math.PI * 2).multiply(20));

		var color = sampleColor(Color.red, Color.yellow);
		var life = sampleNumber(1, 3);
		var size = sampleNumber(2, 10);
		ps.emit(new Particle(newMousePosition, velocity, life, color, size));
		oldMousePosition = newMousePosition;

	}
	ps.simulate(dt);
	ctx.fillStyle = "rgba(0, 0, 0, 0.1)";
	ctx.fillRect(0, 0, canvas.width, canvas.height);
	ps.render(ctx);
}
window.onload = function() {
	canvas = document.getElementById("canvas");
	canvas.style.backgroundColor = "black";

	canvas.onmousemove = function(e) {
		if (e.layerX || e.layerX == 0) { // Firefox
			e.target.style.position = 'relative';
			newMousePosition = new Vector2D(e.layerX, e.layerY);
		} else
			newMousePosition = new Vector2D(e.offsetX, e.offsetY);
	};

	ctx = canvas.getContext("2d");
	ps = new ParticleSystem();
	ps.effectors.push(new ChamberBox(0, 0, 800, 600));
	// start(canvas);
};
function start(can) {
	var interval = 1000.0 / speed;

	function run() {
		step();
		timer = window.setTimeout(run, interval);
	}
	if (!isRunning) {
		isRunning = true;
		run();
	}
}
// function sampleDirection() {
// var theta = Math.random() * 2 * Math.PI;
// return new Vector2D(Math.cos(theta), Math.sin(theta));
// }

function sampleDirection(angle1, angle2) {
	var t = Math.random();
	var theta = angle1 * t + angle2 * (1 - t);
	return new Vector2D(Math.cos(theta), Math.sin(theta));
}

function sampleColor(color1, color2) {
	var t = Math.random();
	return color1.multiply(t).add(color2.multiply(1 - t));
}

function sampleNumber(value1, value2) {
	var t = Math.random();
	return value1 * t + value2 * (1 - t);
}

function stop() {
	clearTimeout(timer);
	isRunning = false;
}

function clearCanvas() {
	// position = new Vector2D(10, 200);
	// velocity = new Vector2D(50, -50);
	ctx.clearRect(0, 0, 800, 600);
}