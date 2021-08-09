import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import { useHistory } from 'react-router-dom'
// import CheckButton from 'react-validation/build/button'
import AuthService from '../services/auth.service'

const Login = () => {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [message, setMessage] = useState()

  const history = useHistory()

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm()

  const onChangeUsername = (e) => {
    setUsername(e.target.value)
  }

  const onChangePassword = (e) => {
    setPassword(e.target.value)
  }

  const handleLogin = (e) => {
    setLoading(true)

    AuthService.login(username, password).then((response) => {
      // console.log(data)
      // login success
      if (response.status === 200) {
        // console.log('login success')
        history.push('/')
      } else if (response.status === 401) {
        console.log(response.data.error)
        // setMessage(response.data.error)
        setMessage(JSON.stringify(response.data))
      } else {
        console.log('login error')
      }
      setLoading(false)
    })
    setLoading(false)
  }

  return (
    <div className="col-md-12">
      <div className="card card-container">
        {/* <img
          src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
          alt="profile-img"
          className="profile-img-card"
        /> */}
        <form onSubmit={handleSubmit(handleLogin)}>
          <div className="form-group">
            <label htmlFor="username">Username</label>
            <input
              {...register('username', { required: true })}
              type="text"
              className="form-control"
              name="username"
              value={username}
              onChange={onChangeUsername}
            />
            {errors?.username?.type === 'required' && (
              <div className="alert alert-danger" role="alert">
                This field is required!
              </div>
            )}
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              {...register('password', { required: true })}
              type="password"
              className="form-control"
              name="password"
              value={password}
              onChange={onChangePassword}
            />
            {errors?.username?.type === 'required' && (
              <div className="alert alert-danger" role="alert">
                This field is required!
              </div>
            )}
          </div>

          <div className="form-group">
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-boder-sm"></span>
              )}
              Login
            </button>
          </div>
          {message && (
            <div className="form-group">
              <div className="alert alert-danger">{message}</div>
            </div>
          )}
        </form>
      </div>
    </div>
  )
}

export default Login
