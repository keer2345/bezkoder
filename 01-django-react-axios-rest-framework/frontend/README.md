<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
**Table of Contents**

- [React Front-end](#react-front-end)
    - [Install Packages](#install-packages)
    - [Import Bootstrap and Router to React CRUD App](#import-bootstrap-and-router-to-react-crud-app)
    - [Add Navbar to React CRUD App](#add-navbar-to-react-crud-app)
    - [Initialize Axios for React CRUD HTTP Client](#initialize-axios-for-react-crud-http-client)
    - [Create React Components](#create-react-components)
    - [Port](#port)

<!-- markdown-toc end -->

# React Front-end

Materials:

- [Axios tutorial](https://www.bezkoder.com/axios-request/)
- [React.js CRUD example to consume Web API](https://www.bezkoder.com/react-crud-web-api/)
- [React Typescript CRUD example with Web API](https://bezkoder.com/react-typescript-axios/)
- [React Firebase using Hooks: CRUD with Realtime Database example](https://www.bezkoder.com/react-firebase-hooks-crud/)

![](https://www.bezkoder.com/wp-content/uploads/2020/03/django-react-axios-rest-framework-crud-example-react-overview.png)
![](https://www.bezkoder.com/wp-content/uploads/2020/11/react-redux-crud-example-rest-api-axios-app-components.png)

## Install Packages

```shell
yarn add React
yarn add react-router-dom
yarn add axios
yarn add bootstrap
```

## Import Bootstrap and Router to React CRUD App

- _index.js_
- _App.js_

## Add Navbar to React CRUD App

## Initialize Axios for React CRUD HTTP Client

- _http-common.js_

```js
import axios from 'axios'

export default axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-type': 'application/json'
  }
})
```

- _services/tutorial.service.js_

```js
import http from '../http-common'

class TutorialDataService {
  getAll() {
    return http.get('/tutorials')
  }
  get(id) {
    return http.get(`/tutorials/${id}`)
  }
  create(data) {
    return http.post('/tutorials', data)
  }
  update(id, data) {
    return http.put(`/tutorials/${id}`, data)
  }
  delete(id) {
    return http.delete(`/tutorials/${id}`)
  }
  deleteAll() {
    return http.delete('/tutorials')
  }
  findByTitle(title) {
    return http.get(`/tutorials?title=${title}`)
  }
}

export default new TutorialDataService()
```

## Create React Components

- _components/AddTutorials.js_
- _components/TutorialsList.js_
- _components/Tutorial.js_

## Port

Create file `.env` on project directory:


```
PORT=8081
```
