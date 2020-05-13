import ImageList from "../components/image-list/ImageList";
import React from "react";
import Folder from "../components/fodler/Folder";
import axios from "axios";

export default class MyFolderPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            structure: [],
            currentPath: "/"
        };
    }

    componentDidMount() {
        this.refreshStructure();
    }

    refreshStructure = () => {
        const username = this.props.match.params.username;
        axios.get(`/api/folder/${username}/structure`)
            .then(res => {
                if (res.status === 200)
                    this.updateTree(res.data);
            })
            .catch(e => {
                this.setState({structure: []});
                console.log(e);
            });
    }

    updateTree = (newTree) => {
        this.setState({structure: newTree});
    }

    move = (path) => {
        this.setState({currentPath: path});
    }

    render() {
        const username = this.props.match.params.username;
        return (
            <React.Fragment>
                <aside style={treeViewStyle}>{
                    <Folder
                        folder={{name: "My Folder", children: this.state.structure}}
                        fullPath="/"
                        currentPath={this.state.currentPath}
                        username={username}
                        onSelect={this.move}
                        onUpdate={this.updateTree}
                    />
                }
                </aside>
                <main style={mainStyle}>
                    <ImageList
                        folderPath={this.state.currentPath}
                        username={this.props.match.params.username}
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
    width: '200px',
    margin: '5px',
    backgroundColor: '#3b1616',
    borderRadius: '10px'
}