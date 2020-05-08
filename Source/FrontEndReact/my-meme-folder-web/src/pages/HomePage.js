import ImageList from "../components/image-list/ImageList";
import React from "react";

export default class HomePage extends React.Component {
    render() {
        return (
            <main style={mainStyle}>
                <ImageList
                    user="1"
                    folderPath="asd"
                />
            </main>
        )
    }
}

const mainStyle = {
    marginLeft: '200px'
}