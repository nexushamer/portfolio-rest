const fs = require('fs');
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

  var sql = "CREATE TABLE profile (user_id varchar(80) NOT NULL,experience_summary varchar(5000) DEFAULT NULL," +
             "last_names varchar(100) DEFAULT NULL," +
             "names varchar(100) DEFAULT NULL," +
             "picture longblob," +
             "twitter_user_id varchar(60) DEFAULT NULL," +
             "PRIMARY KEY (user_id)" +
           ")";
    con.query(sql, function (err, result) {
        if (err) throw err;
        console.log("Table created");
    });


  /*
  sql = "insert into profile(user_id,experience_summary,last_names,names,twitter_user_id) " +
     "values('steve.king@gmail.com','I am a writer of the best horror books','king','stephen','StephenKing');";
  con.query(sql, function (err, result) {
    if (err) throw err;
    console.log("User created");
  });
  */

  const temp_path = './stephen-king.jpg';
  fs.open(temp_path, 'r', function (status, fd) {
      if (status) {
          console.log(status.message);
          return;
      }
      var fileSize = getFileSizeInBytes(temp_path);
      var buffer = new Buffer(fileSize);
      fs.read(fd, buffer, 0, fileSize, 0, function (err, num) {
            sql = "insert into profile(user_id,experience_summary,last_names,names,twitter_user_id,picture)  " +
            "values(?,?,?,?,?,?)";
            const userId = "steve.king@gmail.com";
            const experienceSummary = "I am a writer of the best horror books";
            const lastNames = "king";
            const names = "stephen";
            const twitterUserId = "StephenKing";

            con.query(sql, [userId, experienceSummary, lastNames, names, twitterUserId, buffer],function (err, result) {
              if (err) throw err;
              console.log("User created");
            });
      });
  });
});

function getFileSizeInBytes(filename) {
    var stats = fs.statSync(filename)
    var fileSizeInBytes = stats["size"]
    return fileSizeInBytes
}