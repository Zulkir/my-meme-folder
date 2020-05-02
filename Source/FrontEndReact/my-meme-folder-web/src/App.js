import React from 'react';
import logo from './logo.svg';
import './App.css';
import DummyDataProvider from "./remote/DummyDataProvider";
import Folder from "./components/fodler/Folder";

class App extends React.Component {
    render() {
        const dataProvider = new DummyDataProvider();
        const data = dataProvider.generateAllUserData()['1'];
        console.log(data);
        return (
            <div className="App">
                <header style={headerStyle}>
                    <div style={logoStyle}>My Meme Folder</div>
                </header>
                <nav style={navStyle}>
                    <span style={menuItemStyle}>My Folder</span>
                    <span style={menuItemStyle}>Explore</span>
                </nav>
                <aside style={treeViewStyle}>{
                    data.folders.map(folder => (
                        <Folder folder={folder} />
                    ))
                }
                </aside>
                <main style={mainStyle}>
                    MAIN
                </main>
                <footer style={footerStyle}>
                    Footer
                </footer>
            </div>
        );
    }

}

const headerStyle = {
  width: '100%',
  textAlign: 'left',
  //display: 'inline-block',
  backgroundColor: '#820',
}

const logoStyle = {
  display: 'inline-block',
  color: '#fff',
  fontSize: '50px',
  fontStyle: 'oblique',
  fontWeight: 'bold',
  margin: '10px 20px',
  lineHeight: '80px',
}

const navStyle = {
  padding: '10px',
  fontSize: '18px',
  fontWeight: 'bold',
  color: '#fff',
  backgroundColor: '#820',
  borderWidth: '5px 0px 0px 0px',
  borderColor: '#410',
  borderStyle: 'solid',
}

const treeViewStyle = {
  float: 'left',
  width: '200px'
}

const menuItemStyle = {
  padding: '10px 20px'
}

const mainStyle = {
  marginLeft: '200px'
}

const footerStyle ={
  backgroundColor: '#820',
  color: '#fff',

  clear: 'both',
  height: '100px',
  lineHeight: '100px'
}

export default App;
