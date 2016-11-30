/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
var board = [];
for (var i = 0; i < 30; i++) { //30cols: represents x axis
    board[i] = new Array(20);
    for (var j = 0; j < 20; j++) //20rows: represents y axis
        board[i][j] = {tile: {x: i, y: j}, value: 0};
}

//debug variables
var debugBoard = [];
for(var i=0; i<30; i++){
    debugBoard[i] =  new Array(20);
    for(var j=0; j<20; j++)
        debugBoard[i][j] = 0;
}

function getCell(board, y, x) {
    var NO_VALUE = null;
    var value, hasValue;

    try {
        hasValue = board[y][x] !== undefined;
        value = hasValue ? board[y][x] : NO_VALUE;
    } catch (e) {
        value = NO_VALUE;
    }

    return value;
}

function getNeighbors(board, y, x) {
    return {
        up: getCell(board, y - 1, x),
        right: getCell(board, y, x + 1),
        down: getCell(board, y + 1, x),
        left: getCell(board, y, x - 1)
    };
}


var leftWall = 0;
var rightWall = 29;
var topWall = 0;
var bottomWall =19

// game loop
while (true) {
    var inputs = readline().split(' ');
    var N = parseInt(inputs[0]); // total number of players (2 to 4).
    var P = parseInt(inputs[1]); // your player number (0 to 3).
    for (var i = 0; i < N; i++) {
        var inputs = readline().split(' ');
        var X0 = parseInt(inputs[0]); // starting X coordinate of lightcycle (or -1)

        var Y0 = parseInt(inputs[1]); // starting Y coordinate of lightcycle (or -1)

        var X1 = parseInt(inputs[2]); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)

        var Y1 = parseInt(inputs[3]); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)

        //printErr('X0 '+ X0 + 'Y0 '+ Y0);
        printErr('X1 '+ X1 + 'Y1 '+ Y1);
        debugBoardFunc(X0,Y0,X1,Y1);

        // if(i === P)
        //     board[Y0][X0] = {x:X0, y:Y0};
        //     board[Y1][X1] = {x:X1, y:Y1};
        //     minimax(X1, Y1, 1, true);
    move(X1,Y1);
    }

    // Write an action using print()
    // To debug: printErr('Debug messages...');

}

function minimax(X1, Y1, depth, max ){
    // if(depth == 0)
    //     return board[Y1][X1];

    // if(max){

    // }
    printErr('board of y1 and x1 '+board[Y1][X1].x +' '+board[Y1][X1].y)
}

function move(X1,Y1){
        if(X1 === leftWall || X1 === rightWall)
            print('UP');
        else if(Y1 === topWall || Y1 === bottomWall)
            print('RIGHT')
        else
            print('LEFT');// A single line with UP, DOWN, LEFT or RIGHT
}


function debugBoardFunc(X0,Y0,X1,Y1){
    board[X0][Y0] = {x:1, y:1};
    board[X1][Y1] = {x:1, y:1};

        // for(var i=0; i<30; i++){
        //     board[i].forEach(function(is){ is.forEach(function(his){ printErr(his.x)})});
        // }
    for(var i = 0; i < board.length; i++) {
    var cube = board[i];
    for(var j = 0; j < cube.length; j++) {
        printErr("board[" + i + "][" + j + "] = " + cube[j].x + ' '+cube[j].y);
    }
}
}
