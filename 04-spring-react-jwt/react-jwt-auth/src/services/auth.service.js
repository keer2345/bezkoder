import axios from 'axios'

const API_URL = 'http://localhost:8080/api/auth/'

class AuthService {
  login(username, password) {
    return axios
      .post(API_URL + 'signin', { username, password })
      .then((response) => {
        // console.log(response.data)
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data))
        }

        return response
      })
      .catch((e) => {
        // console.log(e.response.data)
        return e.response
      })
  }
  logout() {
    localStorage.removeItem('user')
  }
  register(username, email, password) {
    return axios.post(API_URL + 'singup', { username, email, password })
  }
  getcurrentUser() {
    return JSON.parse(localStorage.getItem('user'))
  }
}

export default new AuthService()
