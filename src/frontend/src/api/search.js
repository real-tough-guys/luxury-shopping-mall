import axios from "axios";

const config = {
  baseUrl: "/api/products/"
};

function getSearchTitle(name, category) {
  return new Promise((resolve, reject) => {
    axios
      .get(`${config.baseUrl}search`, {
        params: { name: name, category: category }
      })
      .then(res => {
        resolve(res);
      })
      .catch(err => {
        reject(err);
      });
  });
}
export { getSearchTitle };
