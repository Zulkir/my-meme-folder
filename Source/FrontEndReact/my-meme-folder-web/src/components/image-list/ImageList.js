import React from "react";
import DummyDataProvider from "../../remote/DummyDataProvider";
import "./ImageList.css";

export default class ImageList extends React.Component {
    render() {
        const dataProvider = new DummyDataProvider();
        const imageList = dataProvider.getImageList(this.props.user, this.props.folderPath);

        return (
              <div className="image-list-overall">{
                    imageList.map(img => (
                        <div className="image-list-item">
                            <img
                                src={img.thumbnailSrc}
                                alt="img.title"
                            />
                            <div>{img.title}</div>
                        </div>
                    ))
              }</div>
        );
    }
}