export default class Validator {
    static isValid(x, y, r) {
        return this.isValidX(x) && this.isValidY(y) && this.isValidR(r);
    }

    static isValidR(val) {
        const parsedValue = parseInt(val, 10);
        return !isNaN(parsedValue) && parsedValue >= 1 && parsedValue <= 5;
    }

    static isValidX(val) {
        if (Array.isArray(val) && val.length > 0) {
            // Если X - массив и не пуст, проверяем каждый элемент
            return val.every(element => this.isValidXElement(element));
        } else if (typeof val === 'string') {
            // Если x является строкой, разбиваем строку по запятой
            const values = val.split(',').map(element => element.trim()); // Разбиваем и удаляем пробелы
            return values.every(element => this.isValidXElement(element));
        } else {
            // X должен быть строкой или массивом, поэтому если это не строка или массив, или массив пуст, то возвращаем false
            return false;
        }
    }

    static isValidXElement(val) {
        // Проверяем, находится ли val в пределах [-5, 1]
        return val >= -5 && val <= 1;
    }

    static isValidY(val) {
        if (typeof val !== 'string' && typeof val !== 'number') {
            return false;
        }
        const numericY = parseFloat(val.toString().replace(',', '.'));
        const dotCount = (numericY.toString().match(/\./g) || []).length;

        return !isNaN(numericY) && numericY >= -5 && numericY <= 3 && numericY.toString().length < 15 && dotCount === 1;
    }
}
