const url = "Location?Carrier=carrier05";  //esch wahrschinlech sennvoller. well soscht fonktionierts spöter nömm
//const url = "http://localhost:8080/EasyTrack/Location?Carrier=carrier05"; 
const AMOUNF_HISTORY = 50;
const DOT_COLOR_R = 255;
const DOT_COLOR_G = 0;
const DOT_COLOR_B = 0; // neuz builde
const DOT_RADIUS = 30/2;

let history;
let img_url;
let img;


function setup() {
    history = new History(AMOUNF_HISTORY);
    index = 0;
    createCanvas(1152, 648); // dimensionen vom bild angeben
    //canvas.parent('sketch-holder');
    frameRate(5); // 5: 5fps, 1: 1fps
  }
  
  function draw() {
    loadJSON(url, parseData);    
  }

  function parseData(data){
	//console.log(data); // DEBUG
    if (img_url != data.image){
      img_url = data.image;
      img = loadImage(data.image);
      history.clear();
      
    }
    history.addPoint(data.x, data.y);
    drawMap();
  }

  function drawMap(){
	 // console.log(img);
    image(img, 0, 0);
    let coords = history.getPoints();
    let beforeX = map(coords[0].x, 0, 1, 0, img.width);
    let beforeY = map(coords[0].y, 0, 1, 0, img.height);
    for (let i = 0; i < coords.length; i++){
    	
        let alpha = 255 / (coords.length / i);
        var colour = color(DOT_COLOR_R, DOT_COLOR_G, DOT_COLOR_B, alpha);
        let absoluteX = map(coords[i].x, 0, 1, 0, img.width);
        let absoluteY = map(coords[i].y, 0, 1, 0, img.height);
        
        fill(colour);
        noStroke();
        ellipse(absoluteX, absoluteY, DOT_RADIUS, DOT_RADIUS);
        
        stroke(colour);
        line(beforeX, beforeY, absoluteX, absoluteY);
        beforeX = absoluteX;
        beforeY = absoluteY;
       // console.log("X: " + coords[i].x + ", Y: " +  coords[i].y + ", absX: " + absoluteX + ", absY: " + absoluteY);
    } 
  }
  
  /**
   * Damn awesome function. Skaliert einen wert. #BestFunctionEver
   * @param value wert der in relation gesetzt werden soll
   * @param istart min wert der value haben kann
   * @param istop max wert der value haben kann
   * @param ostart begin zielbereich
   * @param ostop ende zielbereich
   * @returns skalierten wert
   */
  function map(value, istart, istop, ostart, ostop) {
	  return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
  }