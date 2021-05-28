import axios from 'axios';

const config = {
    baseUrl: '/api/users/'
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

function signUp(userSignupDto) {
    return new Promise((resolve, reject) => {
        axios.post(`${config.baseUrl}`, userSignupDto)
            .then(response => {
                resolve(response)
                console.log(response)
            })
            .catch(error => {
                reject(error)
                console.error(error)
            })
    })
}


function details() {
    return new Promise((resolve, reject) => {
        axios.get(`${config.baseUrl}/details`)
            .then(response => {
                resolve(response)
                console.log(response)
            })
            .catch(error => {
                reject(error)
                console.error(error)
            })
    })
}


function deleteUser() {
    return new Promise((resolve, reject) => {
        axios.post(`${config.baseUrl}/delete`)
            .then(response => {
                resolve(response)
                console.log(response)
            })
            .catch(error => {
                reject(error)
                console.error(error)
            })
    })
}

function update(userUpdateDto) {
    return new Promise((resolve, reject) => {
        axios.post(`${config.baseUrl}update`, userUpdateDto)
            .then(response => {
                resolve(response)
                console.log(response)
            })
            .catch(error => {
                reject(error)
                console.error(error)
            })
    })
}


function findAll() {
    return new Promise((resolve, reject) => {
        axios.get(`${config.baseUrl}login`)
            .then(response => {
                resolve(response)
                console.log(response)
            })
            .catch(error => {
                reject(error)
                console.error(error)
            })
    })
}

export {
    isDuplicatedEmail,
    signUp,
    details,
    deleteUser,
    update,
    findAll
}