- [Setup ReactJS Project](#setup-reactjs-project)
  - [Materials](#materials)
  - [Create project](#create-project)
  - [Add React Router and Bootstrap](#add-react-router-and-bootstrap)
  - [Create Services](#create-services)
  - [Create React Components for Authentication](#create-react-components-for-authentication)
  - [Create React Components for accessing Resources](#create-react-components-for-accessing-resources)
- [Handle JWT Token expiration in React with Hooks](#handle-jwt-token-expiration-in-react-with-hooks)
  - [How to check when JWT Token is expired](#how-to-check-when-jwt-token-is-expired)
  - [Handle JWT Token expiration with Route changes](#handle-jwt-token-expiration-with-route-changes)

# Setup ReactJS Project

## Materials

- [React Hook Form 7 - Form Validation Example](https://jasonwatmore.com/post/2021/04/21/react-hook-form-7-form-validation-example)

## Create project

```shell
 npx create-react-app react-jwt-auth

 cd react-jwt-auth

yarn add react-router-dom
yarn add bootstrap
yarn add axios
yarn add react-hook-form
yarn add react-validation validator
```

## Add React Router and Bootstrap

_src/index.js_

```js
import { BrowserRouter } from 'react-router-dom'

ReactDOM.render(
  <BrowserRouter>
    <App />
  </BrowserRouter>,
  document.getElementById('root')
)
```

_src/App.js_

```js
import 'bootstrap/dist/css/bootstrap.min.css'

function App() {
  return (
    // ...
  )
}

export default App
```

## Create Services

- _src/services/auth.service.js_
- _src/services/auth.service.js_
- _src/services/user.service.js_

## Create React Components for Authentication

_src/components/login.component.js_
_src/components/register.component.js_
_src/components/profile.component.js_

## Create React Components for accessing Resources

- home.component.js
- boardUser.js
- boardModerator.js
- boardAdmin.js

# Handle JWT Token expiration in React with Hooks

## How to check when JWT Token is expired

There are two ways to check if Token is expired or not.

1. get expiry time in JWT and compare with current time
2. read response status from the server

I will show you the implementations of both ways.

- For 1, we check the token expiration every time the Route changes and call App component logout method.
- For 2, we dispatch logout event to App component when response status tells us the token is expired.

## Handle JWT Token expiration with Route changes

- Details to see [Handle JWT Token expiration in React with Hooks](https://www.bezkoder.com/handle-jwt-token-expiration-react/)
- Source Code Sample at [here](https://github.com/bezkoder/react-redux-hooks-jwt-auth)
