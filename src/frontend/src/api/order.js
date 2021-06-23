import axios from "axios";

const config = {
    baseUrl: "/api/orders/"
};

function orderSave(cartId) {
    return new Promise((resolve, reject) => {
        axios
          .post(`${config.baseUrl}`, {
            orderStatement: cartId
          })
          .then(res => {
            resolve(res);
          })
          .catch(err => {
            reject(err);
          });
    });
}
export { orderSave };
