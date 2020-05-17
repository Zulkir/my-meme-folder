import React from "react";
import "./ImageList.css";
import axios from "axios";
import Modal from 'react-modal';
import ImageUploader from "./ImageUploader";
import userService from "../../services/UserService.js";

export default class ImageList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            imagesWithThumbnails: [],
            viewMode: false,
            viewImage: null,
        };
    }

    componentDidMount() {
        this.refreshList();
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.folderPath === prevProps.folderPath)
            return;
        this.refreshList();
    }

    refreshList = () => {
        const username = this.props.username;
        return axios.get(`/api/folder/${username}/images?folderId=${this.props.folderId}`)
            .then(res => {
                this.setState({imagesWithThumbnails: res.data})
            });
    }

    openImage = (img) => {
        this.setState({
            viewMode: true,
            viewImage: img
        });
    }

    closeImage = () => {
        this.setState({
            viewMode: false,
            viewImage: null
        })
    }

    setTextState = (field, event) => {
        this.setState({
            viewImage: {
                ...this.state.viewImage,
                [field]: event.target.value
            }
        });
    }

    saveImage = (event) => {
        event.preventDefault();
        axios.put("/api/images/" + this.state.viewImage.key, {
            name: this.state.viewImage.name,
            tags: this.state.viewImage.tags
        })
            .then(res => this.refreshList())
            .then(() => this.setState({viewMode: false, viewImage: null}));
    }

    deleteImage = (event) => {
        event.preventDefault();
        axios.delete("/api/images/" + this.state.viewImage.key)
            .then(res => this.refreshList())
            .then(() => this.setState({viewMode: false, viewImage: null}));
    }

    render() {
        const userInfo = userService.getUserInfo();
        const canEdit = userInfo && userInfo.username === this.props.username;
        return (
            <div className="image-list-overall">{
                canEdit ? (
                    <div className="image-list-item">
                        <ImageUploader
                            getFolderId={() => this.props.folderId}
                            onUploaded={this.refreshList}
                        />
                    </div>
                ) : ""
            }{
                this.state.imagesWithThumbnails.map(img => (
                    <div
                        key={img.id}
                        className="image-list-item"
                        onClick={this.openImage.bind(this, img)}
                    >
                        <img
                            src={img.thumbnailSrc}
                            alt="img.title"
                        />
                        <div className="image-list-item-name">{img.name}</div>
                    </div>
                ))}
                <Modal
                    appElement={document.querySelector(".App")}
                    isOpen={this.state.viewMode}
                    onAfterOpen={() => {}}
                    onRequestClose={this.closeImage}
                    style={modalStyle}
                    contentLabel="Example Modal"
                    className="modal"
                    overlayClassName="modal-overlay"
                >{
                    this.state.viewMode ? (
                        <div className="modal-content">
                            <img
                                src={this.state.viewImage.fullImageSource}
                                alt="img.title"
                            />{
                            canEdit ? (
                                <React.Fragment>
                                    <form onSubmit={this.saveImage}>
                                        <div>
                                            <input
                                                type="text"
                                                value={this.state.viewImage.name}
                                                onChange={this.setTextState.bind(this, "name")}
                                                placeholder="Name"
                                            />
                                        </div>
                                        <div>
                                            <input
                                                type="text"
                                                value={this.state.viewImage.tags}
                                                onChange={this.setTextState.bind(this, "tags")}
                                                placeholder="Tags"
                                            />
                                        </div>
                                        <div>
                                            <input
                                                type="submit"
                                                className="submit"
                                                value="Save"
                                            />
                                        </div>
                                    </form>
                                    <form onSubmit={this.deleteImage}>
                                        <div>
                                            <input
                                                type="submit"
                                                className="submit"
                                                value="Delete"
                                            />
                                        </div>
                                    </form>
                                </React.Fragment>
                            ) : (
                                <React.Fragment>
                                    <div>{this.state.viewImage.name}</div>
                                    <div>{this.state.viewImage.tags}</div>
                                </React.Fragment>
                            )}
                        </div>
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