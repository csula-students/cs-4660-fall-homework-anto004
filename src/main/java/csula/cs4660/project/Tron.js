/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
var board = [];
for (var i = 0; i < 30; i++) { //30cols: represents x axis
    board[i] = new Array(20);
    for (var j = 0; j < 20; j++) //20rows: represents y axis
        board[i][j] = {x: i, y: j, value: 0};
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

function getNoOfChildren(neighbors){
    var num = 0;
    for(var neighbor in neighbors){
        if(neighbors[neighbor] !== null)
            num += 1;
    }
    return num;
}

function BFS(X0,Y0){ //for now lets just store values for p1
    //queue
    var queue = [];
    var source = board[Y0][X0] = {x:X0, y:Y0, value:0};

    queue.push(source);
    var exploredSet = new Set();
    exploredSet.add(source);

    while(queue.length !== 0){
        var count =0;
        console.log('level'+ count ++);
        var u = queue.shift();
        var x = u.x;
        var y = u.y;

        var neighbors = getNeighbors(board, y, x);
        var noOfChild = getNoOfChildren(neighbors);
        for(var neighbor in neighbors){ // neighbor -> up,right,down,left
//            console.log(neighbors[neighbor]);
//            if(test === neighbors[neighbor]){
//                console.log('same type safe to add');
//            }
            if(neighbors[neighbor] !== null){
                x = neighbors[neighbor].x;
                y = neighbors[neighbor].y;

                //x and y as acc to the board initialization set the value
                board[x][y].value = 1 / noOfChild;
                if(!exploredSet.has(neighbors[neighbor])){
                    queue.push(neighbors[neighbor]);
                    exploredSet.add(neighbors[neighbor]);
                }
            }
        }
    }
}

function minimax(X1, Y1, depth, max ){
    // if(depth == 0)
    //     return board[Y1][X1];

    // if(max){

    // }
    printErr('board of y1 and x1 '+board[Y1][X1].x +' '+board[Y1][X1].y)
}
BFS(10,10);
//debugBoard();

function debugBoard(){
    //board[X0][Y0] = {x:1, y:1};
    //board[X1][Y1] = {x:1, y:1};

        // for(var i=0; i<30; i++){
        //     board[i].forEach(function(is){ is.forEach(function(his){ printErr(his.x)})});
        // }
    for(var i = 0; i < board.length; i++) {
        var cube = board[i];
        for(var j = 0; j < cube.length; j++) {
            console.log("board[" + i + "][" + j + "] = " + board[i][j].value);
        }
    }
}

//// game loop
//while (true) {
//    var inputs = readline().split(' ');
//    var N = parseInt(inputs[0]); // total number of players (2 to 4).
//    var P = parseInt(inputs[1]); // your player number (0 to 3).
//    for (var i = 0; i < N; i++) {
//        var inputs = readline().split(' ');
//        var X0 = parseInt(inputs[0]); // starting X coordinate of lightcycle (or -1)
//
//        var Y0 = parseInt(inputs[1]); // starting Y coordinate of lightcycle (or -1)
//
//        var X1 = parseInt(inputs[2]); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
//
//        var Y1 = parseInt(inputs[3]); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)
//
//        //printErr('X0 '+ X0 + 'Y0 '+ Y0);
//        printErr('X1 '+ X1 + 'Y1 '+ Y1);
//        debugBoardFunc(X0,Y0,X1,Y1);
//
//        // if(i === P)
//        //     board[Y0][X0] = {x:X0, y:Y0};
//        //     board[Y1][X1] = {x:X1, y:Y1};
//        //     minimax(X1, Y1, 1, true);
//    move(X1,Y1);
//    }
//
//    // Write an action using print()
//    // To debug: printErr('Debug messages...');
//
//}
