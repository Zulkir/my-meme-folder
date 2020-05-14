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
            viewImage: null
        };
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.props.folderPath === prevProps.folderPath)
            return;
        this.refreshList();
    }

    refreshList = () => {
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

    render() {
        const userInfo = userService.getUserInfo();
        const canEdit = userInfo && userInfo.username === this.props.username;
        return (
            <div className="image-list-overall">{
                canEdit ? (
                    <div className="image-list-item">
                        <ImageUploader
                            getPath={() => this.props.folderPath}
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
                        <div>{img.name}</div>
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
                                    <div><input type="text" value={this.state.viewImage.name}/></div>
                                    <div><input type="text" value={this.state.viewImage.tags}/></div>
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