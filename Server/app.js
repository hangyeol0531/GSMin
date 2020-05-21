var express = require('express'); //express 모듈 로드
var bodyParser = require('body-parser');
var app = express(); // express app 생성
const Discord = require('discord.js');
const client = new Discord.Client();
const config = require('../config.json');
const axios = require('axios').default;
var port = 3000;

app.use(bodyParser.json())

function console_all(pr_content){
    console.log(pr_content)

    axios.post(config.web_hook, {
        params: {
            username : 'Server',
            content : pr_content
        }
    });
}


app.get('/', (req,res) =>{

});

app.post('/emailCheck', (req,res)=>{
    console_all("emailCheck 접속");
    console.log("이메일 : " + req.body.email);
    res.send("connect");
});

app.listen(port, function(){
    console.log(`${port}포트로 서버 실행`);
});


//----------------------------------------
client.on('ready', () => {
    console.log(`Logged in as ${client.user.tag}!`);
});



client.login(config.token)

