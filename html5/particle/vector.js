Vector2D = function(x, y) {
	this.x = x;
	this.y = y;
};

Vector2D.prototype = {
	copy : function() {
		return new Vector2D(this.x, this.y);
	},
	length : function() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	},
	sqrLength : function() {
		return this.x * this.x + this.y * this.y;
	},
	normalize : function() {
		var inv = 1 / this.length();
		return new Vector2D(this.x * inv, this.y * inv);
	},
	negate : function() {
		return new Vector2D(-this.x, -this.y);
	},
	add : function(v) {
		return new Vector2D(this.x + v.x, this.y + v.y);
	},
	subtract : function(v) {
		return new Vector2D(this.x - v.x, this.y - v.y);
	},
	multiply : function(f) {
		return new Vector2D(this.x * f, this.y * f);
	},
	divide : function(f) {
		var invf = 1 / f;
		return new Vector2D(this.x * invf, this.y * invf);
	},
	dot : function(v) {
		return this.x * v.x + this.y * v.y;
	}
};

Vector2D.zero = new Vector2D(0, 0);