import React from "react";
import "./ImageList.css";
import axios from "axios";

export default class ImageList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            imagesWithThumbnails: []
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

    render() {
        return (
              <div className="image-list-overall">{
                    this.state.imagesWithThumbnails.map(img => (
                        <div className="image-list-item" key={img.name}>
                            <img
                                src={img.thumbnailSrc}
                                alt="img.title"
                            />
                            <div>{img.name}</div>
                        </div>
                    ))
              }</div>
        );
    }
}