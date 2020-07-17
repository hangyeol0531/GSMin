var fun_all = require('./fun_all.js')
const axios = require('axios');

exports.board = (req,res) =>{
    fun_all.console_all("board 접속");
    var aJsonArray = new Array();
    var aJson = new Object();
    for(var i = 0;  i < 10; i++){
        var aJson = new Object();
        aJson.likeCount = `${i}6`;
        aJson.section = "자유";
        aJson.content = "Lorem Ipsum, g iving information on its origins, as well as a random Lipsum generator.";
        aJson.writer = "양현승";
        aJson.viewer = 32;
        aJson.previous = `${i}초 전`;
        // console.log(aJson)
        aJsonArray.push(aJson);
    }
    // console.log(JSON.stringify(aJsonArray))
    res.end(JSON.stringify(aJsonArray))
}