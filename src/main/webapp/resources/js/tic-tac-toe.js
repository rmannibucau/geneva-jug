var ticTacToe = (function ($) {
    var url = 'ws://' + window.location.host + '/tic-tac-toe';
    var socket = undefined;
    var content = $('#ws-messages');
    var tableSquares = $('.square');
    var connected = false;

    // protocol
    var MOVE = 'MOVE';
    var WIN = 'WIN';
    var EGALITY = 'EGALITY';
    var WRONG_POSITION = 'WRONG_POSITION';

    return {
        reset: function () {
            content.html('');
            tableSquares.html('-');
        },

        play: function (i, j) {
            if (!connected) {
                alert('Not yet connected, please wait or click on Start');
                return;
            }

            socket.send(i + '_' + j);
        },

        connectOrNoop: function () {
            if (connected) {
                return;
            }

            if (window.MozWebSocket) {
                window.WebSocket = window.MozWebSocket;
            }
            if (window.WebSocket) {
                ticTacToe.reset();

                socket = new WebSocket(url);
                socket.onopen = function () {
                    connected = true;
                    content.html("Connected.");
                };
                socket.onclose = function () {
                    content.html("Disconnected.");
                    connected = false;
                };
                socket.onmessage = function (evt) {
                    if (!evt.data) {
                        return;
                    }

                    if (evt.data.substring(0, EGALITY.length) === EGALITY) {
                        alert("No winner!");
                        ticTacToe.disconnect();
                    } else if (evt.data.substring(0, MOVE.length) === MOVE || evt.data.substring(0, WIN.length) === WIN) {
                        var data = evt.data.split(" ");
                        if (data.length == 4) {
                            $('#ttt' + data[1] + data[2]).html(data[3]);
                            if (WIN === data[0]) {
                                alert("Winner is " + data[3] + "!");
                                ticTacToe.disconnect();
                            }
                        }
                    } else if (evt.data.substring(0, WRONG_POSITION.length) === WRONG_POSITION) {
                        content.html(evt.data);
                    }
                };
            } else {
                alert('Websockets are not supported by your browser :(.');
            }
        },

        disconnect: function () {
            if (socket != undefined) {
                socket.close();
                socket = undefined;
            }
            connected = false;
        }
    };
})(jQuery);
