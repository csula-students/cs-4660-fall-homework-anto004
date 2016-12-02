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

function BFS(X0,Y0,X1,Y1){ //for now lets just store values for p1
    //queue
    var queue = [];
    //exploredSet
    var exploredSet = new Set();
    var sourceA = board[Y0][X0] = {x:X0, y:Y0, value:0};
    //var sourceJ = board[Y1][X1] = {x:X1, y:Y1, value:0};


    queue.push(sourceA);
    //queue.push(sourceJ);
    exploredSet.add(sourceA);
   // exploredSet.add(sourceJ);

    while(queue.length !== 0){
        var u = queue.shift();
        var x = u.x;
        var y = u.y;
        //set parent value to 0
        //parents Map
        var parentMap = new Map();

        var neighbors = getNeighbors(board, y, x);
        var noOfChild = getNoOfChildren(neighbors);
        for(var neighbor in neighbors){ // neighbor -> up,right,down,left
//            console.log(neighbors[neighbor]);
//            if(test === neighbors[neighbor]){
//                console.log('same type safe to add');
//            }
            if(neighbors[neighbor] !== null){
                if(!exploredSet.has(neighbors[neighbor])){
                    //(child, parent)
                    parentMap.set(neighbors[neighbor], u);
                    queue.push(neighbors[neighbor]);
                    exploredSet.add(neighbors[neighbor]);
                }
            }
        }
        // at this level
        for(var [key,value] of parentMap.entries()){
//            var parent = parentMap.get(value);
            //setting child value to 1
            key.value = 1 / noOfChild;
        }
    }
}

function minimax(X1, Y1, depth, max ){
    var bestValue;
    if(depth === 0){
        return board[Y1][X1].value;
    }
     if(max){
         var neighbors = getNeighbors(board, Y1, X1);
         for(var neighbor in neighbors){
             if(neighbors[neighbor] !== null){
                var X = neighbors[neighbor].x;
                var Y = neighbors[neighbor].y;

                var startingNodeValue = board[Y1][X1].value;
                var minimaxValue = minimax(X, Y, depth - 1, false);
                bestValue = Math.max(startingNodeValue, minimaxValue);
                //return a board for co-ordinates,
                //reconstruct the board
                //move in that path
                console.log('max'+bestValue);
            }
         }
        return bestValue;
    }
    else{
         var neighbors = getNeighbors(board, Y1, X1);
         for(var neighbor in neighbors){
             if(neighbors[neighbor] !== null){
                var X = neighbors[neighbor].x;
                var Y = neighbors[neighbor].y;

                var startingNodeValue = board[Y1][X1].value;
                var minimaxValue = minimax(X, Y, depth - 1, true);
                bestValue = Math.min(startingNodeValue, minimaxValue);
                console.log('min'+bestValue);
            }
         }
        return bestValue;
    }
}


BFS(0,0, 20,20);
minimax(0,0,2,true);
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

