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
