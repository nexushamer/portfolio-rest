const MYSQL_HOST = process.env.MYSQL_HOST;

if(MYSQL_HOST === '' || MYSQL_HOST === null) {
    process.exit();
}

var mysql = require('mysql');

var con = mysql.createConnection({
  host: MYSQL_HOST,
  user: "root",
  password: "test",
  database: "mysql"
});

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");

  var sql = "CREATE TABLE profile (user_id varchar(80) NOT NULL,experience_summary varchar(500) DEFAULT NULL," +
             "last_names varchar(100) DEFAULT NULL," +
             "names varchar(100) DEFAULT NULL," +
             "picture longblob," +
             "PRIMARY KEY (user_id)" +
           ")";
    con.query(sql, function (err, result) {
        if (err) throw err;
        console.log("Table created");
    });

  sql = "insert into profile(user_id,experience_summary,last_names,names) " +
     "values('usuario01@gmail.com','I am a software developer that loves writing code','patea traseros','pepe');";
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("User created");
    con.end();
  });
});