
var board = [];
for (var i = 0; i < 30; i++) { //30cols: represents x axis
    board[i] = new Array(20);
    for (var j = 0; j < 20; j++) //20rows: represents y axis
        board[i][j] = {tile: {x: i, y: j}, value: 0};
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

function eachCell(board, action, thisArg) {
    var rows = board.length;

    thisArg = (typeof thisArg === 'object') ?
            thisArg : null;

    // Access each row in the matrix
    for (var y = 0; y < rows; y++) {
        var row = board[y];
        var cells = row.length;

        // Access each cell in the row
        for (var x = 0; x < cells; x++) {
            var cell = row[x];

            action.call(thisArg, cell, y, x, board);
        }
    }
}

function BFS(X0,Y0){
    //queue
    var queue = [];
    var source = board[Y0][X0] = {tile:{x:X0, y:Y0}, value:1};

    //var test = board[1][0] = {tile:{x:0, y:1}, value:1};

    queue.push(source);
    var exploredSet = new Set();
    exploredSet.add(source);

    while(queue.length !== 0){
        var u = queue.shift();
        var x = u.tile.x;
        var y = u.tile.y;

        var neighbors = getNeighbors(board, y, x);
        for(var neighbor in neighbors){
//            console.log(neighbors[neighbor]);
//            if(test === neighbors[neighbor]){
//                console.log('same type safe to add');
//            }
            if(!exploredSet.has(neighbors[neighbor])){
                queue.push(neighbors[neighbor])
                exploredSet.add(neighbors[neighbor])
            }
        }
    }
}

BFS(0,0);
//eachCell(board, function (cell, y, x, board) {
//    console.log(
//            'Adjacent cells to [' + y + ', ' + x + ']: ',
//            getNeighbors(board, y, x),
//            'Current Value: ' + cell + '.'
//            );
//
//});

// access the neighbors
//var neighbors = [[]];
//neighbors[0][0] = getNeighbors(board, 0, 0);
//for(var neighbor in neighbors[0][0]){
//   // console.log(neighbor +':'+neighbors[0][0][neighbor])
//    for(var prop in neighbors[0][0][neighbor]){
//        console.log(prop +':'+neighbors[0][0][neighbor][prop] )
//    }
//}

