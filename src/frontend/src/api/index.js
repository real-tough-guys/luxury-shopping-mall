import axios from 'axios';

const config = {
    baseUrl: 'http://localhost:8080/api/users/'
};

function isDuplicatedEmail(email) {
    return new Promise((resolve, reject) => {
        axios.get(`${config.baseUrl}emails/${email}/exists`)
            .then(response => {
                resolve(response);
                console.log(response);
            })
            .catch(error => {
                reject(error);
                console.log(error);
            })
    })
}

function save(userSignupDto) {
    return new Promise((resolve, reject) => {
        const headers = {
            'Content-Type': 'application/json'
        }
        axios.post(`${config.baseUrl}`, userSignupDto)
            .then(response => {
                resolve(response)
                console.log(response)
            }).catch(error => {
            reject(error)
            console.error(error)
        })
    })
}

export {
    isDuplicatedEmail,
    save,
}