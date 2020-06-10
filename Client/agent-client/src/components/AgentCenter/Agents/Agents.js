import React from 'react';
import axios from 'axios';

class Agents extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            runningAgents: [],
            agentClasses: []
        }
    }

    getRunningAgents = () => {
        axios.get("http://192.168.0.20:8080/WarAT2020/rest/client/agents/running")
            .then(response => {
                console.log(response);
                this.setState({
                    runningAgents: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
    }

    startAgentFunction = (type, name) => {
        axios.put("http://192.168.0.20:8080/WarAT2020/rest/client/agents/running/" + type + "/" + name)
            .then(response => {
                console.log(response);
                this.getRunningAgents();
            })
            .catch(error => {
                console.log(error);
            });
    }

    componentDidMount() {
        axios.get("http://192.168.0.20:8080/WarAT2020/rest/client/agents/classes")
            .then(response => {
                console.log(response);
                this.setState({
                    agentClasses: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });

        this.getRunningAgents();
    }

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Agent Types</h2>
                    <hr />
                    {this.state.agentClasses.map(agentClass => {
                        return <p>{agentClass.module}</p>
                    })}
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/agents/classes]
                    [DELETE 192.168.0.20:8080/WarAT2020/rest/client/agents/running/..aid..]
                    <br />
                    <h2>Start Agent</h2>
                    <hr />
                    <button className="btn" onClick={() => this.startAgentFunction("AgentBean","bean agent")}>Agent Bean</button>
                    <button className="btn" onClick={() => this.startAgentFunction("Master","master agent")}>Master</button>
                    <button className="btn" onClick={() => this.startAgentFunction("Collector","collect agent")}>Collector</button>
                    <button className="btn" onClick={() => this.startAgentFunction("Searcher","search agent")}>Searcher</button>
                </div>
                <div className="col-8">
                    <h2>Running Agents</h2>
                    <hr />
                    {this.state.runningAgents.map(agent => {
                        return <p><strong>{agent.name}</strong> | host: {agent.host.address} | type: {agent.type.type}</p>
                    })}
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/agents/running]
                </div>
            </div>
        );

    }
}

export default Agents;