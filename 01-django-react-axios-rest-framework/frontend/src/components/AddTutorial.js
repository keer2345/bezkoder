import React, { useState } from 'react'
import TutorialDataService from '../services/tutorial.service'

const AddTutorial = () => {
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [submit, setSubmit] = useState(false)

  const changeDescription = (e) => {
    setDescription(e.target.value)
  }

  const saveTutorial = () => {
    var data = {
      title: title,
      description: description
    }
    TutorialDataService.create(data)
      .then((response) => {
        setTitle('')
        setDescription('')
        setSubmit(true)

        console.log(data)
      })
      .catch((e) => {
        console.log(e)
      })
  }

  return (
    <>
      <div className="submit-form">
        {submit ? (
          <div>
            <h4>You submitted successfully!</h4>
            <button
              className="btn btn-success"
              onClick={() => setSubmit(false)}
            >
              Add
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                required
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                name="title"
              />
            </div>
            <div className="form-group">
              <label htmlFor="description">Description</label>
              <input
                type="text"
                className="form-control"
                id="description"
                required
                value={description}
                onChange={changeDescription}
                name="description"
              />
            </div>
            <button onClick={saveTutorial} className="btn btn-success">
              Submit
            </button>
          </div>
        )}
      </div>
    </>
  )
}

export default AddTutorial
