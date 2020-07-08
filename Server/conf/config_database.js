const mysql = require('mysql');
const config = require('../../config.json');

var db = mysql.createConnection({
    host: "localhost",
    database: config.dbname,
    user: "root",
    password: config.sqlpassword
  })

  module.exports = db;