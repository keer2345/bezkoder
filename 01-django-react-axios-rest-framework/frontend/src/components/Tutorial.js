import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import TutorialDataService from '../services/tutorial.service'

const Tutorial = () => {
  const { id } = useParams()
  const [currentTutorial, setCurrentTutorial] = useState(null)

  useEffect(() => {
    getTutorial(id)
  }, [id])

  const getTutorial = (id) => {
    TutorialDataService.get(id)
      .then((resp) => setCurrentTutorial(resp.data))
      .catch((e) => console(e))
  }
  const onChangeTitle = (e) => {
    setCurrentTutorial((prevState) => {
      return { ...prevState, title: e.target.value }
    })
  }
  const onChangeDescription = (e) => {
    setCurrentTutorial((prevState) => {
      return { ...prevState, description: e.target.value }
    })
  }
  const deleteTutorial = () => {
    TutorialDataService.delete(id).catch((e) => console.log(e))
  }
  const updateTutorial = () => {
    TutorialDataService.update(id, currentTutorial)
      // .then((resp) => console.log(resp.data))
      .catch((e) => console.log(e))
  }

  return (
    <div>
      {currentTutorial ? (
        <div className="edit-form">
          <h4>Tutorial</h4>
          <form>
            <div className="form-group">
              <label htmlFor="title">Title</label>
              <input
                type="text"
                className="form-control"
                id="title"
                value={currentTutorial.title}
                onChange={onChangeTitle}
              />
            </div>
            <div className="form-group">
              <label htmlFor="description">Description</label>
              <input
                type="text"
                className="form-control"
                id="description"
                value={currentTutorial.description}
                onChange={onChangeDescription}
              />
            </div>
            <div className="form-group">
              <label>
                <strong>Status:</strong>
              </label>
              {currentTutorial.published ? 'Published' : 'Pending'}
            </div>
          </form>

          <button onClick={deleteTutorial}>Delete</button>

          <button type="submit" onClick={updateTutorial}>
            Update
          </button>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Tutorial...</p>
        </div>
      )}
    </div>
  )
}

export default Tutorial
