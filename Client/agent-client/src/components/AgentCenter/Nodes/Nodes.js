import React from 'react';
import axios from 'axios';

class Nodes extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            hostAddress: "",
            hostPort: "",
            hostAlias: "",
            masterAddress: "192.168.0.20",
            masterPort: "8080",
            nodes: []
        }
    }

    componentDidMount() {
        axios.get(this.props.masterURL + "/client/host")
            .then(response => {
                console.log(response);
                this.setState({
                    hostAlias: response.data.alias.substr(0, response.data.alias.length - 5),
                    hostAddress: response.data.address.substr(0, response.data.address.length - 5),
                    hostPort: response.data.address.substr(response.data.address.length - 4, 4)
                })
            })
            .catch(error => {
                console.log(error);
            })

            axios.get(this.props.masterURL + "/connection")
            .then(response => {
                console.log(response);
                this.setState({
                    nodes: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
    }

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Master Node Info</h2>
                    <hr />
                    <div className="this-node-info">
                        <p>IP Address: <span>{this.state.masterAddress}</span></p>
                        <p>Port: <span>{this.state.masterPort}</span></p>
                    </div>
                    <br />
                    <h2>This Node Info {this.state.hostAddress === this.state.masterAddress ? <h4>[master]</h4> : null}</h2>
                    <hr />
                    <div className="this-node-info">
                        <p>IP Address: <span>{this.state.hostAddress}</span></p>
                        <p>Port: <span>{this.state.hostPort}</span></p>
                        <p>Alias: <span>{this.state.hostAlias}</span></p>
                    </div>
                </div>
                <div className="col-8">
                    <h2>Other Nodes</h2>
                    <hr />
                    <div className="all-nodes">
                        <table>
                            <tr><td><strong>IP Address</strong></td><td><strong>Port</strong></td></tr>
                            {this.state.nodes.map(node => <tr><td>{node.substr(0, node.length - 5)}</td><td>{node.substr(node.length - 4, 4)}</td></tr>)}
                            
                        </table>
                    </div>
                </div>
            </div>
        );

    }
}

export default Nodes;