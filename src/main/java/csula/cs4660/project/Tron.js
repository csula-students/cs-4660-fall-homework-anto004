

var board = [];
for (var i = 0; i < 20; i++) { //20cols: represents x axis
    board[i] = new Array(30);
    for (var j = 0; j < 30; j++) //30rows: represents y axis
        //board[y][x]
        board[i][j] = {x: j, y: i, value: 0};
}
var width = 30;
var height = 20;


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

//fill(1), old(0)
//height = 29, width = 19
function floodFillNorth(x, y, fill, old, enemy, numOfSpaces) {
    if ((x < 0) || (x >= width))
        return numOfSpaces;
    if ((y < 0) || (y >= height))
        return numOfSpaces;
    if (board[y][x].value === old) {
        //increment numOfSpaces for this path
        numOfSpaces++;
        return floodFillNorth(x, y-1, fill, old, enemy, numOfSpaces);
    }
    else if(board[y][x].value === 1){
        return numOfSpaces;
    }
    else
        return numOfSpaces;
}
function floodFillEast(x, y, fill, old, enemy, numOfSpaces) {
    if ((x < 0) || (x >= width))
        return numOfSpaces;
    if ((y < 0) || (y >= height))
        return numOfSpaces;
    if (board[y][x].value === old) {
        //increment numOfSpaces for this path
        numOfSpaces++;
        return floodFillEast(x+1, y, fill, old, enemy, numOfSpaces);
    }
    else if(board[y][x].value === 1){
        return numOfSpaces;
    }
    else
        return numOfSpaces;
}
function floodFillSouth(x, y, fill, old, enemy, numOfSpaces) {
    if ((x < 0) || (x >= width))
        return numOfSpaces;
    if ((y < 0) || (y >= height))
        return numOfSpaces;

    if (board[y][x].value === old) {
        //increment numOfSpaces for this path
        numOfSpaces++;
        return floodFillSouth(x, y+1, fill, old, enemy, numOfSpaces);
    }
    else if(board[y][x].value === 1){
        return numOfSpaces;
    }
    else
        return numOfSpaces;
}
function floodFillWest(x, y, fill, old, enemy, numOfSpaces) {
    if ((x < 0) || (x >= width))
        return numOfSpaces;
    if ((y < 0) || (y >= height))
        return numOfSpaces;
    if (board[y][x].value === old) {
        //increment numOfSpaces for this path
        numOfSpaces++;
        return floodFillWest(x-1, y, fill, old, enemy, numOfSpaces);
    }
    else if(board[y][x].value === 1){
        return numOfSpaces;
    }
    else
        return numOfSpaces;
}
function floodFill(x, y, fill, old, enemy, numOfSpaces){
    var north = floodFillNorth(x, y-1, fill, old, enemy, numOfSpaces);
    //console.log('north:'+north);

    var east = floodFillEast(x+1, y, fill, old, enemy, numOfSpaces);
    //console.log('east:'+east);

    var south = floodFillSouth(x, y+1, fill, old, enemy, numOfSpaces);
    //console.log('south:'+south);

    var west = floodFillWest(x-1, y, fill, old, enemy, numOfSpaces);
    //console.log('west'+west);
    //set this traversed path as filled
    board[y][x].value = fill;
    if(north >= east && north >= south && north >= west){
        return 'UP';
    }
    else if(east >= north && east >= south && east >= west){
        return 'RIGHT';
    }
    else if(south >= north && south >= east && south >= west){
        return 'DOWN';
    }
    else
        return 'LEFT';
}

function BFS(boardCopy, X1, Y1, maxDepth){ //for now lets just store values for p1
    if(maxDepth < 0){
        return;
    }
    //queue
    var queue = [];
    //exploredSet
    var exploredSet = new Set();
    boardCopy[Y1][X1].value = 0;
    var sourceA = boardCopy[Y1][X1];
    //var sourceJ = board[Y1][X1] = {x:X1, y:Y1, value:0};

    var currentDepth = 0;
    var elementsToDepthIncrease = 1;
    var nextElementsToDepthIncrease = 0;

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

        var neighbors = getNeighbors(boardCopy, y, x);
        var noOfChild = getNoOfChildren(neighbors);
        //limit bfs to noOfChild levels
        nextElementsToDepthIncrease = noOfChild;
        if(--elementsToDepthIncrease === 0){
            if(++currentDepth > maxDepth)
                return;
            elementsToDepthIncrease = nextElementsToDepthIncrease;
            nextElementsToDepthIncrease = 0;
        }
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
        for(var [key,values] of parentMap.entries()){
//            var parent = parentMap.get(value);
            //setting child value to 1
            // value 2 is assigned to enemy cell
            //if(key.value !== 2)
            var x = key.x;
            var y = key.y;
            var north = floodFillNorth(x, y-1, 1, 0, 2, 0);
            var east = floodFillEast(x+1, y, 1, 0, 2, 0);
            var south = floodFillSouth(x, y+1, 1, 0, 2, 0);
            var west = floodFillWest(x-1, y, 1, 0, 2, 0);
            key.value = north + east + south + west;
        }
    }
}

function minimax(boardCopy, X1, Y1, depth, max ){
    var bestValue = {};

    if(depth === 0){
       // console.log('board'+X1+','+Y1+'is: '+boardCopy[Y1][X1].value);
        return boardCopy[Y1][X1];
    }

    var neighbors = getNeighbors(boardCopy, Y1, X1);
    if(max){
        for(var neighbor in neighbors){
           // console.log(neighbors[neighbor])
            if(neighbors[neighbor] !== null){
               var X = neighbors[neighbor].x;
               var Y = neighbors[neighbor].y;

               var startingNode = boardCopy[Y1][X1];
               //console.log('MAX startin: '+startingNode.x +','+startingNode.y);
               var minimaxValue = minimax(boardCopy, X, Y, depth - 1, false);
              // console.log('MAX mini '+minimaxValue.value+' dir '+neighbor);
               if(minimaxValue.value > startingNode.value){
                   boardCopy[Y1][X1].value = minimaxValue.value;
                   bestValue = minimaxValue;
               }
               else{
                   bestValue = startingNode;
               }
                //return a board for co-ordinates,
                //reconstruct the board
                //move in that path
            }
        }
       // console.log('max'+bestValue);
        return bestValue;
    }
    else{
         for(var neighbor in neighbors){
             if(neighbors[neighbor] !== null){
                var X = neighbors[neighbor].x;
                var Y = neighbors[neighbor].y;

                var startingNode = boardCopy[Y1][X1];
               // console.log('MIN startin: '+startingNode.x +','+startingNode.y);
                var minimaxValue = minimax(boardCopy, X, Y, depth - 1, true);
               // console.log('MIN miniValue'+minimaxValue.value);
                if(startingNode.value > minimaxValue.value){
                    boardCopy[Y1][X1].value = minimaxValue.value;
                    //return board of the min value of the two
                    bestValue = minimaxValue;
                }
                else{
                    bestValue = boardCopy[Y1][X1];
                }
            }
        }
       // console.log('min'+bestValue);
        return bestValue;
    }
}

function distBetPlayers(X1, Y1, X2, Y2){
    var dx = Math.abs(X1 - X2);
    var dy = Math.abs(Y1 - Y2);

    return dx + dy;
}

//At start game enemy pos is at the bottom right
var enemyPosX = 29;
var enemyPosY = 19;
var enemyBoardStatus = new Map();
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
        //printErr('X1 '+ X1 + 'Y1 '+ Y1);
        //debugBoardFunc(X0,Y0,X1,Y1);

        if(i === P){
            board[Y0][X0].value = 1;

            var dist = distBetPlayers(X1, Y1, enemyPosX, enemyPosY);
//            if(dist <= 4){
//                //minimax
//                var boardCopy = board.slice();
//
//                BFS(boardCopy, X1, Y1);
//                var moveMin;
//                var moveBoard = minimax(boardCopy, X1, Y1, 2, true);
//                var neighbors = getNeighbors(boardCopy, Y1, X1);
//                for(var neighbor in neighbors){
//                if(neighbors[neighbor] !== null){
//                    if(neighbors[neighbor].value === moveBoard.value){
//                        moveMin = neighbor;
//                    }
//                }
//            }
//            print(moveMin);
//            }
//            else{
                var move = floodFill(X1,Y1,1,0,2,0);
                print (move);
            //}
        }
        else{
            enemyPosX = X1;
            enemyPosY = Y1;
            // if enemy loses
            if(X0 === -1 && Y0 === -1){
                for(var [key, value] of enemyBoardStatus.entries()){
                    // Reset each cells that belonged to enemy to 0
                    key.value = 0;
                }
            }
            else{
                board[Y0][X0].value = 2;
                board[Y1][X1].value = 2;
                enemyBoardStatus.set(board[Y0][X0], board[Y0][X0]);
                enemyBoardStatus.set(board[Y1][X1], board[Y1][X1]);
            }
        }
    }
    // Write an action using print()
    // To debug: printErr('Debug messages...');
}

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