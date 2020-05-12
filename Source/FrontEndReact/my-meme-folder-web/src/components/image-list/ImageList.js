import React from "react";
import "./ImageList.css";
import axios from "axios";
import Modal from 'react-modal';

export default class ImageList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            imagesWithThumbnails: [],
            editMode: false,
            editImage: null
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.folderPath === prevProps.folderPath)
            return;
        const username = this.props.username;
        axios.get(`/api/folder/${username}/images?path=${encodeURI(this.props.folderPath)}`)
            .then(res => {
                this.setState({imagesWithThumbnails: res.data})
            })
            .catch(e => {
                console.log(e);
            });
    }

    openImage = (img) => {
        this.setState({
            editMode: true,
            editImage: img
        });
    }

    closeImage = () => {
        this.setState({
            editMode: false,
            editImage: null
        })
    }

    render() {
        return (
              <div className="image-list-overall">{
                    this.state.imagesWithThumbnails.map(img => (
                        <div
                            key={img.name}
                            className="image-list-item"
                            onClick={this.openImage.bind(this, img)}
                        >
                            <img
                                src={img.thumbnailSrc}
                                alt="img.title"
                            />
                            <div>{img.name}</div>
                        </div>
                    ))}
                    <Modal
                        appElement={document.querySelector(".App")}
                        isOpen={this.state.editMode}
                        onAfterOpen={console.log}
                        onRequestClose={this.closeImage}
                        style={modalStyle}
                        contentLabel="Example Modal"
                    >{
                        this.state.editMode ? (
                            <React.Fragment>
                                <span>ASDASD ASD ASD AS D D</span>
                                <img
                                    src={this.state.editImage.thumbnailSrc}
                                    alt="img.title"
                                />
                            </React.Fragment>
                        ) : (<React.Fragment/>)
                    }</Modal>
              </div>
        );
    }
}

const modalStyle = {
    backgroundColor: 'blue',
    color: 'black'
}