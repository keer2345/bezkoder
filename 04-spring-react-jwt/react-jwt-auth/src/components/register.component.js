import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import AuthService from '../services/auth.service'

const Register = () => {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [message, setMessage] = useState()

  const {
    register,
    handleSubmit,
    formState: { errors }
  } = useForm()

  const onChangeUsername = (e) => {
    setUsername(e.target.value)
  }

  const onChangeEmail = (e) => {
    setEmail(e.target.value)
  }

  const onChangePassword = (e) => {
    setPassword(e.target.value)
  }

  const handleRegister = (e) => {
    AuthService.register(username, email, password).then((resp) => {
      if (resp === 200) {
        console.log(resp)
        setMessage(resp.data.message)
      } else {
        // console.log(resp.data.message)
        setMessage(resp.data.message)
      }
    })
  }

  return (
    <div className="col-md-12">
      <div className="card card-container">
        <form onSubmit={handleSubmit(handleRegister)}>
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
            <label htmlFor="email">Email</label>
            <input
              {...register('email', { required: true })}
              type="text"
              className="form-control"
              name="email"
              value={email}
              onChange={onChangeEmail}
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
            <button className="btn btn-primary btn-block">Sign Up</button>
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

export default Register
