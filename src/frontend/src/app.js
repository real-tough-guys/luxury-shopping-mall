const cors = require("cors");

app.use(cors({
    origin:"http://localhost:8080",
    credentials:true
}))