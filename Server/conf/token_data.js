const jwt = require('jsonwebtoken');
const db = require('./config_database')
const config = require('../../config.json');

exports.receive_token_inforation = (req, res) =>{
    try{
        // decoded_token = jwt.verify(req.body.user_token, config.secret_key)
        // res.end(decoded_token)
        jwt.verify(req.body.user_token, config.secret_key, (err,decoded) =>{
            if(err){
                console.log(err)
            }else{
                res.end(JSON.stringify(decoded))
            }
        })
    }catch(err){
        console.log(err)
        res.end('token is invalid')
    }
}
    
// app.post('/token', (req, res) =>{
//     const payload = {
//         idx : req.body.idx,
//         email : req.body.email,
//         nickname : req.body.nickname,
//         grade : req.body.grade
//     }
//     var token = jwt.sign(payload, config.secret_key); //첫번째 인자 : playlaod 2번째 인자 비밀키 값
//     console.log(token);
//     try{
//         decoded_token = jwt.verify(token, config.secret_key)
//         console.log(decoded_token)
//         res.end('token is ggul dda ri')
//     }catch{
//         res.end('token is invalid')
//     }
// })