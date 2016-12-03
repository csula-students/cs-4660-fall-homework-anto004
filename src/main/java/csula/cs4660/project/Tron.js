/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
var board = [];
for (var i = 0; i < 30; i++) { //30cols: represents x axis
    board[i] = new Array(20);
    for (var j = 0; j < 20; j++) //20rows: represents y axis
        board[i][j] = {x: j, y: i, value: 0};
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
        UP: getCell(board, y - 1, x),
        RIGHT: getCell(board, y, x + 1),
        DOWN: getCell(board, y + 1, x),
        LEFT: getCell(board, y, x - 1)
    };
}

function getNoOfChildren(parent){
    var num = 0;
    for(var child in parent){
        if(parent[child] !== null)
            num += 1;
    }
    return num;
}

function BFS(X0,Y0){ //for now lets just store values for p1
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

function minimax(Y1, X1, depth, max ){
    var bestValue = {};

    if(depth === 0){
        return {value: board[Y1][X1].value};
    }

    var neighbors = getNeighbors(board, Y1, X1);
    if(max){
        for(var neighbor in neighbors){
           // console.log(neighbors[neighbor])
            if(neighbors[neighbor] !== null){
               var X = neighbors[neighbor].x;
               var Y = neighbors[neighbor].y;

               var startingNode = {value: board[Y1][X1].value, dir: neighbor};
               var minimaxValue = minimax(Y, X, depth - 1, false);
               if(startingNode.value > minimaxValue.value){
                   bestValue = startingNode;
               }
               else{
                   minimaxValue.dir = neighbor;
                   bestValue = minimaxValue;
               }
                //return a board for co-ordinates,
                //reconstruct the board
                //move in that path
            }
        }
        console.log('max'+bestValue.dir);
        return bestValue;
    }
    else{
         for(var neighbor in neighbors){
             if(neighbors[neighbor] !== null){
                var X = neighbors[neighbor].x;
                var Y = neighbors[neighbor].y;

                var startingNode = {value: board[Y1][X1].value, dir: neighbor};
                var minimaxValue = minimax(Y, X, depth - 1, true);
                 if(startingNode.value > minimaxValue.value){
                    bestValue = startingNode;
                }
                else{
                    minimaxValue.dir = neighbor;
                    bestValue = minimaxValue;
                }
            }
         }
         console.log('min'+bestValue.dir);
        return bestValue;
    }
}


//BFS(10,19);
//minimax(10,19,2,true);
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
//        //printErr('X1 '+ X1 + 'Y1 '+ Y1);
//        //debugBoardFunc(X0,Y0,X1,Y1);
//
//        if(i === P){
//            BFS(X0,Y0);
//            var move = minimax(X0, Y0, "LEFT", 2, true);
//            print(move.dir);
//        }
//    }
//
//    // Write an action using print()
//    // To debug: printErr('Debug messages...');
//
//}

