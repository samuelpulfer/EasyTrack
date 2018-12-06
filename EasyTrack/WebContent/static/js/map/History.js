class History{

    constructor(count){
        this.count = count;
        this.elements = new Array(0);
    }

    addPoint(x, y){
        this.elements.push(new Coord(x, y));
        if (this.elements.length > this.count){
            this.elements.shift();
        }
    }

    getPoints(){
        return this.elements;
    }
    clear(){
        this.elements = new Array(0);
    }
}