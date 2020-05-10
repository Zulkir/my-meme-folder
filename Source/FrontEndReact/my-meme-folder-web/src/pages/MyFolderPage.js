import ImageList from "../components/image-list/ImageList";
import React from "react";
import Folder from "../components/fodler/Folder";
import DummyDataProvider from "../remote/DummyDataProvider";

export default class MyFolderPage extends React.Component {
    render() {
        const dataProvider = new DummyDataProvider();
        const data = dataProvider.getMyFolderPageData('1');
        return (
            <React.Fragment>
                <aside style={treeViewStyle}>{
                    data.folders.map(folder => {
                        const fullPath = `/${folder.name}`;
                        return (
                            <Folder folder={folder} key={fullPath} fullPath={fullPath}/>
                        );
                    })
                }
                </aside>
                <main style={mainStyle}>
                    <ImageList
                        user="1"
                        folderPath="asd"
                    />
                </main>
            </React.Fragment>
        )
    }
}

const mainStyle = {
    marginLeft: '200px'
}

const treeViewStyle = {
    float: 'left',
    width: '200px'
}