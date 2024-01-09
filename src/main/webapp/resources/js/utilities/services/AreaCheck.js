export default class Validator {
    static isHit(x, y, r) {
        return this.isCircle(x, y, r) || this.isRectangle(x, y, r) || this.isTriangle(x, y, r);
    }

    static isRectangle(x, y, r) {
        return y >= -r && x >= -r / 2 && x <= 0 && y <= 0;
    }

    static isCircle(x, y, r) {
        return x * x + y * y <= (r * r) && x <= 0 && y >= 0;
    }

    static isTriangle(x, y, r) {
        return x >= 0 && y >= 0 && y <= -x + r;
    }
}