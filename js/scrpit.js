// constant variables
const minRows = 5
const minCols = 5
const maxRows = 7
const maxCols = 13

// global variables
var turn = ""
var player1 = ""
var player2 = ""
var columns
var rows

/**
 *  First function when game starts.
 */
function startGame(){

    if(checkFields()){
        turn = "red"
        document.getElementById("start").value = "RESTART GAME"
        setPointerEvents("none");
        loadBoard()
    }
}

/**
 * Set the pointer events of the game.
 */
function setPointerEvents(value){
    document.getElementById("board").style.pointerEvents = value == "none" ? "auto" : "none" 
    document.getElementById("pl1").style.pointerEvents   = value  
    document.getElementById("pl2").style.pointerEvents   = value  
    document.getElementById("cols").style.pointerEvents  = value 
    document.getElementById("rows").style.pointerEvents  = value 
}

/**
 * Check all fields of the initial form.
 */
function checkFields(){
    player1 = document.getElementById("pl1").value;
    player2 = document.getElementById("pl2").value;
    columns = document.getElementById("cols").value;
    rows = document.getElementById("rows").value;

    let txtErrorNameX = document.getElementById("error_name_x").style
    let txtErrorNameO = document.getElementById("error_name_o").style
    let txtErrorNCols = document.getElementById("error_n_cols").style
    let txtErrorNRows = document.getElementById("error_n_rows").style

    txtErrorNameX.visibility = player1 == "" ? "visible" : "hidden" 
    txtErrorNameO.visibility = player2  == "" ? "visible" : "hidden" 

    let correctCols = columns  != "" && !isNaN(parseInt(columns))  && columns >= minCols && columns <= maxCols
    let correctRows = rows     != "" && !isNaN(parseInt(rows)) && rows >= minRows && rows <= maxRows 
    

    txtErrorNCols.visibility =  !correctCols ? "visible" : "hidden" 
    txtErrorNRows.visibility =  !correctRows ? "visible" : "hidden" 

    return player1 != "" && player2 != "" && correctRows && correctCols
}

/**
 * Load all of the board structure dynamically using an HTML table.
 */
function loadBoard(){
    document.getElementById("desc").style.display = "none"
    table = "<table>"
    for (let i = 0; i < rows; i++) {
        table += "<tr>"
        for (let j = 0; j < columns; j++) {
            table += `<td id="${i}_${j}" onmouseover="hoverColumn(${j})" onmouseout="resetColumn(${j})"><div id="${i},${j}"  onclick="putTile(${i},${j})" class="tile white"></div></td>`
        }
        table += "</tr>"
    }
    document.getElementById("board").innerHTML = table + "</table>"
}

/**
 * When mouse pass over the colum, the appareance will change.
 */
function hoverColumn(col){
    for (let i = 0; i < rows; i++) {
        let cell = document.getElementById(`${i},${col}`)
        if(cell.classList.contains("white")){
            cell.textContent = "⬇️"
        }
    }
    let row = getRow(col)
    if(!isNaN(row)){
        let tile = document.getElementById(`${row},${col}`)
        tile.style.backgroundColor = `${turn}`
        tile.style.opacity = "70%"
        tile.textContent = ""
    }
}

/**
 * When mouse stop stepping over the colum, the appareance will come to normal
 */
function resetColumn(col){
    let row = getRow(col)

    if(!isNaN(row)){
        let tile = document.getElementById(`${row},${col}`)
        tile.style.backgroundColor = `white`
        tile.style.opacity = "100%"
    }

    for (let i = 0; i < rows; i++) {
        let cell = document.getElementById(`${i},${col}`)
        if(cell.classList.contains("white")){
            cell.textContent = ""
        }
    }
}

/**
 * Insert new tile into the board
 */
function putTile(row, col){
    row = getRow(col)

    if(!isNaN(row)){
        let tile = document.getElementById(`${row},${col}`)
        tile.classList.remove("white")
        tile.classList.add(`${turn}`)
        tile.style.opacity = "100%"
        tile.textContent = ""

        if(checkWinner(col, row)){
            win()
        }else if(isDraw()) {
            draw();
        }

        turn = turn == "red" ? "yellow" : "red"
    }
}

/**
 * Check if is a draw (all tiles are not white)
 */
function isDraw(){
    return document.getElementsByClassName("white").length == 0;
}

/**
 * Call stopGame function with the phrase and the color of it.
 */
function draw(){
    stopGame(`Draw`,"white")
}

function win(){
    let winnerName = turn == "red" ? player1 : player2
    stopGame(`${winnerName} won the game`,turn)
}

/**
 * Set the pointer events of the game and show the finalPhrase with a color.
 */
function stopGame(finalPhrase,color){

    let desc = document.getElementById("desc")
    desc.style.display = "block"
    desc.innerHTML = finalPhrase
    desc.style.color = color
    document.getElementById("start").value = "PLAY AGAIN"
    setPointerEvents("auto")
}

/**
 * Check if there are already a winner.
 */
function checkWinner(col, row){
    if(checkWinnerByOrientation(row,col,"ver") || checkWinnerByOrientation(col,row,"hor")){
        return true
    }

    let initCol = col - 3
    let finCol = col + 3

    let initRow = row - 3
    let finRow = row + 3

    count = 0
    let i = initCol
    let j = initRow

    while(i <= finCol && j<=finRow){
        let tile = document.getElementById(`${j},${i}`)
        if(tile != null && tile.classList.contains(turn)){
            count++
            if(count == 4){
                return true
            }
        }else{
            count = 0
        }
        i++
        j++
    }


    count = 0
    i = initCol
    j = finRow
    while(i <= finCol && j>=initRow){
        let tile = document.getElementById(`${j},${i}`)
        if(tile != null && tile.classList.contains(turn)){
            count++
            if(count == 4){
                return true
            }
        }else{
            count = 0
        }
        i++
        j--
    }
    return false
}

/**
 * Check if there are a winner by orienation.
 */
function checkWinnerByOrientation(point1,point2, orientation){
    let count = 0
    for (let i = point2 - 3; i <= point2 + 3; i++) {
        let str = orientation == "ver" ? `${point1},${i}` : `${i},${point1}`
        let tile = document.getElementById(str)
        if(tile != null && tile.classList.contains(turn)){
            count++
            if(count == 4){
                return true
            }
        }else{
            count = 0
        }
    }
}

/**
 * Return the number of the first row whic is empty (white).
 */
function getRow(col){
    for (let i = rows-1; i >= 0 ; i--) {
        let tile = document.getElementById(`${i},${col}`)
        if(tile.classList.contains('white')){
            return i
        }
    }

}

