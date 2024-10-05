
const minRows = 5
const minCols = 5
const maxRows = 7
const maxCols = 13

var turn = "red"
var player1 = ""
var player2 = ""
var columns
var rows

function startGame(){

    if(checkFields()){
        // player1.style.pointerEvents = "none"
        // player2.style.pointerEvents = "none"
        // columns.style.pointerEvents = "none"
        // rows.style.pointerEvents = "none"

        loadBoard()
    }

}


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
        }
        turn = turn == "red" ? "yellow" : "red"
    }

}

function win(){

    let winnerName = turn == "red" ? player1 : player2
    let desc = document.getElementById("desc")
    desc.style.display = "block"
    desc.innerHTML = `${winnerName} won the game`
    desc.style.color = turn

    for (let i = 0; i < rows; i++) {
        for (let j = 0; j < columns; j++) {
            document.getElementById(`${i}_${j}`).style.pointerEvents = "none"
        }
    }

    document.getElementById("start").value = "PLAY AGAIN"

}


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

function getRow(col){

    for (let i = rows-1; i >= 0 ; i--) {
        let tile = document.getElementById(`${i},${col}`)
        if(tile.classList.contains('white')){
            return i
        }
    }

}

