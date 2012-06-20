var Particle = function(position, velocity, life, color, size) {
	this.position = position;
	this.velocity = velocity;
	this.acceleration = Vector2D.zero;
	this.age = 0;
	this.life = life;
	this.color = color;
	this.size = size;
};

function ParticleSystem() {
	var that = this;
	var particles = new Array();

	function applyGravity() {
		for ( var i in particles)
			particles[i].acceleration = that.gravity;
	}

	function kinematics(dt) {
		for ( var i in particles) {
			var p = particles[i];
			p.position = p.position.add(p.velocity.multiply(dt));
			p.velocity = p.velocity.add(p.acceleration.multiply(dt));
		}
	}
	// Public fields
	this.gravity = new Vector2D(0, 100);
	this.effectors = new Array();

	// Public methods

	this.emit = function(particle) {
		particles.push(particle);
	};

	this.simulate = function(dt) {
		aging(dt);
		applyGravity();
		applyEffectors();
		kinematics(dt);
	};

	// ...

	// Private methods

	function aging(dt) {
		for ( var i = 0; i < particles.length;) {
			var p = particles[i];
			p.age += dt;
			if (p.age >= p.life)
				kill(i);
			else
				i++;
		}
	}

	function kill(index) {
		if (particles.length > 1)
			particles[index] = particles[particles.length - 1];
		particles.pop();
	}

	function applyEffectors() {
		for ( var j in that.effectors) {
			var apply = that.effectors[j].apply;
			for ( var i in particles)
				apply(particles[i]);
		}
	}

	this.render = function(ctx) {
		for ( var i in particles) {
			var p = particles[i];
			var alpha = 1 - p.age / p.life;
			ctx.fillStyle = "rgba(" + Math.floor(p.color.r * 255) + ","
					+ Math.floor(p.color.g * 255) + ","
					+ Math.floor(p.color.b * 255) + "," + alpha.toFixed(2)
					+ ")";
			ctx.beginPath();
			ctx.arc(p.position.x, p.position.y, p.size, 0, Math.PI * 2, true);
			ctx.closePath();
			ctx.fill();
		}
	};
}

function ChamberBox(x1, y1, x2, y2) {
	this.apply = function(particle) {
		if (particle.position.x - particle.size < x1
				|| particle.position.x + particle.size > x2)
			particle.velocity.x = -particle.velocity.x;

		if (particle.position.y - particle.size < y1
				|| particle.position.y + particle.size > y2)
			particle.velocity.y = -particle.velocity.y;
	};
}